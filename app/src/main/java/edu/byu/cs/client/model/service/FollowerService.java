package edu.byu.cs.client.model.service;

import java.io.IOException;

import com.example.shared.model.domain.User;
import edu.byu.cs.client.model.net.ServerFacade;

import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.IFollowerService;
import com.example.shared.model.service.request.FollowerRequest;
import com.example.shared.model.service.response.FollowerResponse;
import edu.byu.cs.client.util.ByteArrayUtils;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowerService implements IFollowerService {


    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. Uses the {@link ServerFacade} to
     * get the followers from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followers.
     */
    public FollowerResponse getFollowers(FollowerRequest request) throws IOException, TweeterRemoteException {
        FollowerResponse response = getServerFacade().getFollowers(request);

        if(response.isSuccess()) {
            loadImages(response);
        }

        return response;
    }

    /**
     * Loads the profile image data for each follower included in the response.
     *
     * @param response the response from the follower request.
     */
    private void loadImages(FollowerResponse response) throws IOException {
        for(User user : response.getFollowers()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }

    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}