package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.FollowerService;

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
            } catch (RuntimeException | IOException e) {
                String message = "[Bad Request]";
                throw new RuntimeException(message, e);
            }
        }
}
