package com.example.server.dao;

import com.example.shared.model.service.request.FollowingRequest;
import com.example.shared.model.service.response.FollowingResponse;

public class FolloweeDAO {
    DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    public FollowingResponse getFollowees(FollowingRequest request) {
        return dataProvider.getFollowees(request);
    }
}
