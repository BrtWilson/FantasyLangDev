package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.FollowStatusService;
import com.example.shared.model.net.RemoteException;

import java.io.IOException;

public class GetFollowStatusHandler implements RequestHandler<FollowStatusRequest, FollowStatusResponse> {

    @Override
    public FollowStatusResponse handleRequest(FollowStatusRequest request, Context context) {
        FollowStatusService service = new FollowStatusService();
        try {
            return service.getFollowStatus(request);
        }catch (RuntimeException | IOException | RemoteException e) {
            String message = "[Bad Request]";
            throw new RuntimeException(message, e);
        }
    }
}
