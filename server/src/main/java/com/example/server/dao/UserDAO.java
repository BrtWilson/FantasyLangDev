package com.example.server.dao;

import com.example.shared.model.service.request.UserRequest;
import com.example.shared.model.service.response.UserResponse;

public class UserDAO {
    DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    public UserResponse getUserByAlias(UserRequest userRequest) {
        return dataProvider.getUserByAlias(userRequest);
    }
}