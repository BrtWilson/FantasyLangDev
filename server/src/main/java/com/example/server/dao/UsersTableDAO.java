package com.example.server.dao;

import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.request.UserRequest;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.LogoutResponse;
import com.example.shared.model.service.response.RegisterResponse;
import com.example.shared.model.service.response.UserResponse;

public class UsersTableDAO {
    DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    public UserResponse getUserByAlias(UserRequest userRequest) {
        return dataProvider.getUserByAlias(userRequest);
    }

    public LoginResponse login(LoginRequest request) {
        return dataProvider.login(request);
    }

    public LogoutResponse logout(LogoutRequest request) {
        return dataProvider.logout(request);
    }

    public RegisterResponse register(RegisterRequest request) {
        return dataProvider.register(request);
    }
}