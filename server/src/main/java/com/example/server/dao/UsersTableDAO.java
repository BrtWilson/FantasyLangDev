package com.example.server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.example.server.dao.dbstrategies.DynamoDBStrategy;
import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.FollowStatusRequest;
import com.example.shared.model.service.request.FollowerRequest;
import com.example.shared.model.service.request.FollowingRequest;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.request.UserRequest;
import com.example.shared.model.service.response.BasicResponse;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.RegisterResponse;
import com.example.shared.model.service.response.UserResponse;

import java.util.ArrayList;

public class UsersTableDAO {
    //DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    private static final String tableName = "Users";
    private static final String keyAttribute = "Alias";

    private static final String attributeFirstName = "FirstName";
    private static final String attributeLastName = "LastName";
    private static final String attributePassword = "Password";
    private static final String attributeImageUrl = "ImageUrl";
    private static final String attributeFollowerCount = "FollowerCount";
    private static final String attributeFolloweeCount = "FolloweeCount";

    private static final String FAULTY_USER_REQUEST = "[Bad Request]";
    private static final String SERVER_SIDE_ERROR = "[Server Error]";

    public UserResponse getUserByAlias(UserRequest userRequest) {
        try {
            Item retrievedUser = (Item) DynamoDBStrategy.basicGetItem(tableName, keyAttribute, userRequest.getAlias());
            User tempUser = new User();

            tempUser.setAlias(retrievedUser.getString(keyAttribute));
            tempUser.setFirstName(retrievedUser.getString(attributeFirstName));
            tempUser.setLastName(retrievedUser.getString(attributeLastName));
            tempUser.setPassword(retrievedUser.getString(attributePassword));
            tempUser.setFolloweeCount(retrievedUser.getString(attributeFolloweeCount));
            tempUser.setFollowerCount(retrievedUser.getString(attributeFollowerCount));
            tempUser.setImageUrl(retrievedUser.getString(attributeImageUrl));

            return new UserResponse(tempUser);
        } catch (Exception e) {
            return new UserResponse(e.getMessage());
        }
    }

    public LoginResponse login(LoginRequest request) {
        try {
            Item retrievedUser = (Item) DynamoDBStrategy.basicGetItem(tableName, keyAttribute, request.getUsername());
            if (retrievedUser != null) {
                User tempUser = new User();
                tempUser.setAlias(retrievedUser.getString(keyAttribute));
                tempUser.setFirstName(retrievedUser.getString(attributeFirstName));
                tempUser.setLastName(retrievedUser.getString(attributeLastName));
                tempUser.setPassword(retrievedUser.getString(attributePassword));
                tempUser.setFolloweeCount(retrievedUser.getString(attributeFolloweeCount));
                tempUser.setFollowerCount(retrievedUser.getString(attributeFollowerCount));
                tempUser.setImageUrl(retrievedUser.getString(attributeImageUrl));

                if (tempUser.getPassword() == request.getPassword()) { // Note that hashing has already happened in the Services
                    AuthTableDAO authTableDAO = new AuthTableDAO();
                    AuthToken token = authTableDAO.startingAuth(request.getUsername());
                    return new LoginResponse(tempUser, token);
                } else { return new LoginResponse(FAULTY_USER_REQUEST + ": Password does not match."); }
            } else { return new LoginResponse(FAULTY_USER_REQUEST + ": User does not exist."); }

        } catch (Exception e) {
            return new LoginResponse(FAULTY_USER_REQUEST + ": " + e.getMessage());
        }
    }

    public BasicResponse logout(LogoutRequest request) {
        //VERIFY: whether this needs additional checks. Is there something else for this?
        AuthTableDAO authTableDAO = new AuthTableDAO();
        if (authTableDAO.logoutToken(request)) {
            return new BasicResponse(true, "Logout successful.");
        } else {
            return new BasicResponse(false, SERVER_SIDE_ERROR + ": Logout failed.");
        }
    }

    public RegisterResponse register(RegisterRequest request) {
        try {
            Object userTableMatch = DynamoDBStrategy.basicGetItem(tableName, keyAttribute, request.getUserName());
            if (userTableMatch != null) {
                return new RegisterResponse(FAULTY_USER_REQUEST + ": User already exists.", false);
            } else {
                User registeredUser = UploadUser(request);
                AuthTableDAO authTableDAO = new AuthTableDAO();
                AuthToken token = authTableDAO.startingAuth(request.getUserName());
                return new RegisterResponse(registeredUser, token, true);
            }
        } catch (Exception e) {
            return new RegisterResponse(SERVER_SIDE_ERROR + ": " + e.getMessage(), false);
        }
    }

    private User UploadUser(RegisterRequest request) throws Exception {
        String alias = request.getUserName();
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String password = request.getPassword();
        String imageUrl = request.getEncodedImage();
        User user = new User(firstName, lastName, alias, imageUrl);
        user.setPassword(password);

        ArrayList<String> attributes = new ArrayList<>();
        attributes.add(attributeFirstName);
        attributes.add(attributeLastName);
        attributes.add(attributePassword);
        attributes.add(attributeImageUrl);
        attributes.add(attributeFollowerCount);
        attributes.add(attributeFolloweeCount);

        ArrayList<String> attributeValues = new ArrayList<>();
        attributeValues.add(firstName);
        attributeValues.add(lastName);
        attributeValues.add(password);
        attributeValues.add(imageUrl);
        attributeValues.add("0");
        attributeValues.add("0");

        if (DynamoDBStrategy.createItemWithAttributes(tableName, keyAttribute, alias, attributes, attributeValues)) {
            return user;
        }
        throw new Exception(SERVER_SIDE_ERROR + ": Failed to complete user upload");
    }


    public boolean unfollow(FollowStatusRequest request) throws Exception {
        Integer followerCount = Integer.valueOf(getUserFollowerCount(request.getOtherUser())) + 1;
        Integer followeeCount = Integer.valueOf(getUserFolloweeCount(request.getCurrentUser())) + 1;
        DynamoDBStrategy.updateItemStringAttribute(tableName, keyAttribute, request.getOtherUser(), attributeFollowerCount, followerCount.toString());
        DynamoDBStrategy.updateItemStringAttribute(tableName, keyAttribute, request.getOtherUser(), attributeFolloweeCount, followeeCount.toString());
        return true;
    }

    private String getUserFollowerCount(String usarAlias) {
        User currentUser = (User) DynamoDBStrategy.basicGetItem(tableName,keyAttribute,usarAlias);
        return currentUser.getFollowerCount();
    }

    private String getUserFolloweeCount(String usarAlias) {
        User currentUser = (User) DynamoDBStrategy.basicGetItem(tableName,keyAttribute,usarAlias);
        return currentUser.getFolloweeCount();
    }

    public boolean follow(FollowStatusRequest request) throws Exception {
        Integer followerCount = Integer.parseInt(getUserFollowerCount(request.getOtherUser())) + 1;
        Integer followeeCount = Integer.parseInt(getUserFolloweeCount(request.getCurrentUser())) + 1;
        DynamoDBStrategy.updateItemStringAttribute(tableName, keyAttribute, request.getOtherUser(), attributeFollowerCount, followerCount.toString());
        DynamoDBStrategy.updateItemStringAttribute(tableName, keyAttribute, request.getOtherUser(), attributeFolloweeCount, followeeCount.toString());
        return true;
    }

    public int getNumFollowing(FollowingRequest request) {
        return Integer.parseInt(getUserFolloweeCount(request.getFollowingAlias()));
    }

    public int getNumFollower(FollowerRequest request) {
        return Integer.parseInt(getUserFollowerCount(request.getUserAlias()));
    }

    /*
    private HashMap<String, User> getUserTableInfo() {
        return dataProvider.setUpUsers();
    }

    private User getAUser() {
        return dataProvider.getSampleDummyUser();
    }
    */
}