package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.FollowService;
import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.request.FollowRequest;
import com.example.shared.model.service.response.FollowResponse;

import java.io.IOException;

public class FollowHandler implements RequestHandler<FollowRequest, FollowResponse> {

    @Override
    public FollowResponse handleRequest(FollowRequest request, Context context) {
        FollowService service = new FollowService();
        try {
            return service.followResponse(request);
        }catch (RuntimeException | TweeterRemoteException |IOException e) {
                String message = "[Bad Request]";
                throw new RuntimeException(message, e);
            }
    }
}
