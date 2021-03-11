package com.example.server.dao;

import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.RegisterResponse;

public class RegisterDAO {
    DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    public RegisterResponse register(RegisterRequest request) {
        return dataProvider.register(request);
    }
}
