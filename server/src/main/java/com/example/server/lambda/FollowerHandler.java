package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.FollowerService;
import com.example.shared.model.service.request.FollowerRequest;
import com.example.shared.model.service.request.FollowingRequest;
import com.example.shared.model.service.response.FollowerResponse;
import com.example.shared.model.service.response.FollowingResponse;

import java.io.IOException;

/**
 * An AWS lambda function that allows the user to follow other people
 */

public class FollowerHandler implements RequestHandler<FollowerRequest, FollowerResponse> {
        @Override
        public FollowerResponse handleRequest(FollowerRequest followerRequest, Context context) {
            FollowerService followerService = new FollowerService();
            try {
                return followerService.getFollowers(followerRequest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
}
