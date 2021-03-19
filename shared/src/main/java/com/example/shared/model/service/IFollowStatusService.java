package com.example.shared.model.service;

import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.request.FollowStatusRequest;
import com.example.shared.model.service.response.FollowStatusResponse;

import java.io.IOException;

public interface IFollowStatusService {
    FollowStatusResponse getFollowStatus(FollowStatusRequest request) throws IOException, TweeterRemoteException;
}
