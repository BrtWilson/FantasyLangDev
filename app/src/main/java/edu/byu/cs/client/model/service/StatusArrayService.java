package edu.byu.cs.client.model.service;

import java.io.IOException;

import com.example.shared.model.domain.User;
import com.example.shared.model.domain.Status;
import edu.byu.cs.client.model.net.ServerFacade;

import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.IStatusArrayService;
import com.example.shared.model.service.request.IListRequest;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.request.UserRequest;
import com.example.shared.model.service.response.IListResponse;
import com.example.shared.model.service.response.StatusArrayResponse;
import edu.byu.cs.client.util.ByteArrayUtils;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class StatusArrayService implements IStatusArrayService {


    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link ServerFacade} to
     * get the followees from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public StatusArrayResponse requestStatusArray(IListRequest request) {
        StatusArrayResponse response = new StatusArrayResponse("Statuses missing");
        try {
            if (request.getClass() != StatusArrayRequest.class) {
                return response;
            }
            StatusArrayRequest req = (StatusArrayRequest) request;
            response = getServerFacade().getStatusArray(req);

            if (response.isSuccess()) {
                loadImages(response);
            }
        } catch (IOException | TweeterRemoteException e) {
            response = new StatusArrayResponse("Statuses error: " + e.getMessage());
            return response;
        }

        return response;
    }

    @Override
    public IListResponse getList(IListRequest listRequest) {
        return requestStatusArray(listRequest);
    }

    /**
     * Loads the profile image data for each followee included in the response.
     *
     * @param response the response from the followee request.
     */
    private void loadImages(StatusArrayResponse response) throws IOException, TweeterRemoteException {
        for(Status status : response.getStatuses()) {
            User user = getUser(status.getCorrespondingUserAlias());
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }

    private User getUser(String correspondingUserAlias) throws IOException, TweeterRemoteException {
        UserService userService = new UserService();
        return userService.getUserByAlias(new UserRequest(correspondingUserAlias)).getUser();
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
