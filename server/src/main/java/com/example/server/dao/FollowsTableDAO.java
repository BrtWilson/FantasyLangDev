package com.example.server.dao;

import com.example.shared.model.service.request.FollowerRequest;
import com.example.shared.model.service.request.FollowingRequest;
import com.example.shared.model.service.response.FollowerResponse;
import com.example.shared.model.service.response.FollowingResponse;

public class FollowsTableDAO {
    DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    public FollowerResponse getFollowers(FollowerRequest request) {
        return dataProvider.getFollowers(request);
    }

    public FollowingResponse getFollowees(FollowingRequest request) {
        return dataProvider.getFollowees(request);
    }
}
