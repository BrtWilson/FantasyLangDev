package com.example.server.dao;

import com.example.server.dao.dbstrategies.DynamoDBStrategy;
import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.request.UserRequest;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.BasicResponse;
import com.example.shared.model.service.response.RegisterResponse;
import com.example.shared.model.service.response.UserResponse;
import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;

import java.util.ArrayList;
import java.util.HashMap;

public class UsersTableDAO {
    //DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    private static final String tableName = "Users";
    private static final String keyAttribute = "Alias";

    private static final String attributeFirstName = "FirstName";
    private static final String attributeLastName = "LastName";
    private static final String attributePassword = "Password";
    private static final String attributeImageUrl = "ImageUrl";

    private static final String FAULTY_USER_REQUEST = "Invalid_Request"; // Todo: check this to match 400/500 page
    private static final String SERVER_SIDE_ERROR = "Server_Error";

    public UserResponse getUserByAlias(UserRequest userRequest) {
        try {
            User retrievedUser = (User) DynamoDBStrategy.basicGetItem(tableName, keyAttribute, userRequest.getAlias());
            return new UserResponse(retrievedUser);
        } catch (Exception e) {
            return new UserResponse(e.getMessage());
        }
    }

    public LoginResponse login(LoginRequest request) {
        //return dataProvider.login(request);
        try {
            //TODO
            User retrievedUser = (User) DynamoDBStrategy.basicGetItem(tableName, keyAttribute, request.getUsername());
            if (retrievedUser != null) {
                if (retrievedUser.getPassword() == request.getPassword()) { // Note that hashing has already happened in the Services
                    AuthTableDAO authTableDAO = new AuthTableDAO();
                    AuthToken token = authTableDAO.startingAuth(request.getUsername());
                    return new LoginResponse(retrievedUser, token);
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
            Object userTableMatch = DynamoDBStrategy.basicQueryWithKey(tableName, keyAttribute, request.getUserName());
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
        //TODO: UPLOAD WITH DYNAMO
        ArrayList<String> attributes = new ArrayList<>();
        attributes.add(attributeFirstName);
        attributes.add(attributeLastName);
        attributes.add(attributePassword);
        attributes.add(attributeImageUrl);

        ArrayList<Object> attributeValues = new ArrayList<>();
        attributeValues.add(firstName);
        attributeValues.add(lastName);
        attributeValues.add(password);
        attributeValues.add(imageUrl);

        if (DynamoDBStrategy.createItemWithAttributes(tableName, keyAttribute, alias, attributes, attributeValues)) {
            return user;
        }
        throw new Exception("Failed to complete user upload");
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