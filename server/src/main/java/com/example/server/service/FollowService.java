package com.example.server.service;

import com.example.server.dao.FollowsTableDAO;
import com.example.server.dao.UsersTableDAO;
import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.IFollowService;
import com.example.shared.model.service.request.FollowRequest;
import com.example.shared.model.service.response.FollowResponse;

import java.io.IOException;

public class FollowService implements IFollowService{

    @Override
    public FollowResponse followResponse(FollowRequest followRequest) throws IOException, TweeterRemoteException {
        return null;
    }
}
