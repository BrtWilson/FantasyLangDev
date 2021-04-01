package com.example.server.dao;

import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.request.UserRequest;
import com.example.shared.model.service.response.BasicResponse;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.RegisterResponse;
import com.example.shared.model.service.response.UserResponse;

import java.util.HashMap;

public class AuthTableDAO {
    DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    //TODO: Add to logging in and logging out these authorization mechanisms

    public Boolean getAuthorized(AuthToken authToken) {
        //get whether the AuthToken is expired
        return true;
    }

    public AuthToken loginAuth(LoginRequest request /*or AuthToken*/) {
        //create AuthToken in table
        return new AuthToken();
    }

    public Boolean logoutToken(LogoutRequest request) {
        //delete AuthToken

        return true;
    }

    public AuthToken registerAuth(RegisterRequest request /*or AuthToken*/) {
        //create AuthToken in table
        return new AuthToken();
    }

    //TODO: Add authorization functions for other services

}