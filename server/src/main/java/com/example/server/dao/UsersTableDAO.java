package com.example.server.dao;

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

import java.util.HashMap;

public class UsersTableDAO {
    DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    private static final String tableName = "Users";

    public UserResponse getUserByAlias(UserRequest userRequest) {
        return new UserResponse(getAUser());
    }

    public LoginResponse login(LoginRequest request) {
        //return dataProvider.login(request);
        HashMap<String, User> usersMap = getUserTableInfo();

        if (usersMap != null) {
            if (usersMap.containsKey(request.getUsername())) {
                User user = usersMap.get(request.getUsername());
                if (user.getPassword().equals(request.getPassword())) {
                    //loggedInUser = user;
                    return new LoginResponse(user, new AuthToken());
                }
                return new LoginResponse("Password does not match.");
            }
            return new LoginResponse("Username does not exist.");
        }
        return new LoginResponse("User does not exist.");
    }

    public BasicResponse logout(LogoutRequest request) {
        //return dataProvider.logout(request);
        //VERIFY: whether this needs additional checks. Is there really a service we need for this?

        return new BasicResponse(true, "Logout successful.");
    }

    public RegisterResponse register(RegisterRequest request) {
        //return dataProvider.register(request);
        HashMap<String, User> usersMap = getUserTableInfo();

        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String alias = request.getUserName();
        String password = request.getPassword();
//        String url = request.getImageURL();
        String url = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
        User user = new User(firstName, lastName, alias, url);

        if (!usersMap.containsKey(user.getAlias())) {
            user.setPassword(password);
            usersMap.put(user.getAlias(), user);
            //loggedInUser = user;
            return new RegisterResponse(user, new AuthToken(), true);
        }
        return new RegisterResponse("Username already taken. User different username.", false);
    }

    private HashMap<String, User> getUserTableInfo() {
        return dataProvider.setUpUsers();
    }

    private User getAUser() {
        return dataProvider.getSampleDummyUser();
    }

}