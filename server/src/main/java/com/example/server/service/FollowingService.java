package com.example.server.service;

import com.example.server.dao.FollowsTableDAO;
import com.example.shared.model.service.IFollowingService;
import com.example.shared.model.service.request.FollowingRequest;
import com.example.shared.model.service.response.FollowingResponse;

import java.io.IOException;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowingService implements IFollowingService {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link} to
     * get the followees from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request) throws IOException {
        FollowingResponse response = getFolloweesDao().getFollowees(request);

        return response;
    }


    /**
     * Returns an instance of {@link}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    public FollowsTableDAO getFolloweesDao() {
        return new FollowsTableDAO();
    }
}
