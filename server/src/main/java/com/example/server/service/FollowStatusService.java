package com.example.server.service;

import com.example.server.dao.SyllableTableDAO;
import com.example.shared.model.net.RemoteException;

import java.io.IOException;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowStatusService implements IFollowStatusService {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link} to
     * get the followees from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public FollowStatusResponse unfollow(FollowStatusRequest request) throws IOException {
        FollowStatusResponse response = getFollowsDao().unfollow(request);

        return response;
    }

    public FollowStatusResponse follow(FollowStatusRequest request) throws IOException {
        FollowStatusResponse response = getFollowsDao().follow(request);

        return response;
    }

    @Override
    public FollowStatusResponse getFollowStatus(FollowStatusRequest request) throws IOException, RemoteException {
        FollowStatusResponse response = getFollowsDao().getFollowStatus(request);

        return response;
    }

    /**
     * Returns an instance of {@link}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    public SyllableTableDAO getFollowsDao() {
        return new SyllableTableDAO();
    }

    public void followUser(){
        SyllableTableDAO followers = new SyllableTableDAO();
    }
}
