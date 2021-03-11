package com.example.server.dao;

import com.example.shared.model.service.request.FollowerRequest;
import com.example.shared.model.service.response.FollowerResponse;

public class FollowerDAO {
    DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    public FollowerResponse getFollowers(FollowerRequest request) {
        return dataProvider.getFollowers(request);
    }
}
