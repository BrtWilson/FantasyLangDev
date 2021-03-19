package edu.byu.cs.client.model.service;

import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.IFollowStatusService;
import com.example.shared.model.service.request.FollowStatusRequest;
import com.example.shared.model.service.response.FollowStatusResponse;

import java.io.IOException;

import edu.byu.cs.client.model.net.ServerFacade;

/**
 * Contains business logic for getting and updating whether the current user follows a given user
 */
public class FollowStatusService implements IFollowStatusService {

    /**
     * Determines whether to simply check the follow status or to follow/unfollow the given user
     * @param request the request information
     * @return a response indicating success of the operation
     */
    @Override
    public FollowStatusResponse getFollowStatus(FollowStatusRequest request) throws IOException, TweeterRemoteException {
        if (request.getRequestType() == FollowStatusRequest.GET)
            return getServerFacade().getFollowStatus(request);
        else if (request.getRequestType() == FollowStatusRequest.FOLLOW)
            return getServerFacade().follow(request);
        else
            return getServerFacade().unfollow(request);
    }

    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    public ServerFacade getServerFacade() { return new ServerFacade(); }
}

