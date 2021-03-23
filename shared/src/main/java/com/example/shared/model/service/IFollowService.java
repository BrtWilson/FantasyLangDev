package com.example.shared.model.service;

import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.request.FollowRequest;
import com.example.shared.model.service.response.FollowResponse;

import java.io.IOException;

public interface IFollowService {
    public FollowResponse followResponse(FollowRequest request) throws IOException, TweeterRemoteException;
}
