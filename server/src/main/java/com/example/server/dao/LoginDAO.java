package com.example.server.dao;

import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.response.LoginResponse;

public class LoginDAO {
    DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    public LoginResponse login(LoginRequest request) {
        return dataProvider.login(request);
    }
}
