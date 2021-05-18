package edu.byu.cs.client.model.service.oldfiles;

import java.io.IOException;

import com.example.shared.model.domain.User;

import edu.byu.cs.client.model.net.ServerFacade;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.IStatusArrayService;
import com.example.shared.model.service.request.IListRequest;
import com.example.shared.model.service.request.UpdateSyllablesRequest;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.response.IListResponse;
import com.example.shared.model.service.response.DictionaryPageResponse;
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
    public DictionaryPageResponse requestStatusArray(IListRequest request) {
        DictionaryPageResponse response = new DictionaryPageResponse("Statuses missing");
        try {
            if (request.getClass() != UpdateSyllablesRequest.class) {
                return response;
            }
            UpdateSyllablesRequest req = (UpdateSyllablesRequest) request;
            response = getServerFacade().getStatusArray(req);

            if (response.isSuccess()) {
                loadImages(response);
            }
        } catch (IOException | RemoteException e) {
            response = new DictionaryPageResponse("Statuses error: " + e.getMessage());
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
    private void loadImages(DictionaryPageResponse response) throws IOException, RemoteException {
        if (response.getStatuses().size() == 0)
            return;
        for(Status status : response.getStatuses()) {
            User user = getUser(status.getCorrespondingUserAlias());
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }

    private User getUser(String correspondingUserAlias) throws IOException, RemoteException {
        UserService userService = new UserService();
        return userService.getUserByAlias(new GetLanguageDataRequest(correspondingUserAlias)).getUser();
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
