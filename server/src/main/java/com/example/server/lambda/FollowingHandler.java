package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.example.shared.model.service.request.FollowingRequest;
import com.example.shared.model.service.response.FollowingResponse;
import com.example.server.service.FollowingService;

import java.io.IOException;

//import edu.byu.cs.client.server.service.FollowingServiceImpl;

/**
 * An AWS lambda function that returns the users a user is following.
 */
public class FollowingHandler implements RequestHandler<FollowingRequest, FollowingResponse> {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the followees.
     */
    @Override
    public FollowingResponse handleRequest(FollowingRequest request, Context context) {
        FollowingService service = new FollowingService();
        try {
            return service.getFollowees(request);
        } catch (RuntimeException | IOException e) {
            String message = "[Bad Request]";
            throw new RuntimeException(message, e);
        }
    }
}
