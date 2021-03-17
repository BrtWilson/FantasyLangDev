package com.example.server.service;

import com.example.server.dao.FollowsTableDAO;
import com.example.shared.model.service.IFollowerService;
import com.example.shared.model.service.request.FollowerRequest;
import com.example.shared.model.service.response.FollowerResponse;

import java.io.IOException;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowerService implements IFollowerService {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. Uses the {} to
     * get the followers from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followers.
     */
    public FollowerResponse getFollowers(FollowerRequest request) throws IOException {
        FollowerResponse response = getFollowersDao().getFollowers(request);

        return response;
    }


    /**
     * Returns an instance of {@link}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    FollowsTableDAO getFollowersDao() {
        return new FollowsTableDAO();
    }
}