package com.example.server.dao;

import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.response.NewStatusResponse;

public class PostStatusDAO {
    DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    public NewStatusResponse pushNewStatus(NewStatusRequest request) {
        return dataProvider.pushNewStatus(request);
    }
}