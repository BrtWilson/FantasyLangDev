package com.example.server.dao;

import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.response.LogoutResponse;

public class LogoutDAO {
    DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    public LogoutResponse logout(LogoutRequest request) {
        return dataProvider.logout(request);
    }
}
