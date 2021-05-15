package com.example.server.service;

import com.example.server.dao.LanguageTableDAO;
import com.example.server.dao.DictionaryTableDAO;
import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.INewStatusService;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.response.FollowerResponse;
import com.example.shared.model.service.response.NewStatusResponse;

import java.io.IOException;

/**
 * Contains the business logic to support posting a new status
 */
public class NewStatusFeedService implements INewStatusService {

    private static final String SERVER_SIDE_ERROR = "[Server Error]";

    public NewStatusResponse postStatusBatch(NewStatusRequest request, FollowerResponse followerResponse) throws IOException {

        //NewStatusResponse feedResponse = getFeedTableDAO().postNewStatus(request);
        try {
            NewStatusResponse feedResponse = getFeedTableDAO().postStatusBatch(request, followerResponse);
            return feedResponse;
        } catch (Exception e) {
            return new NewStatusResponse(SERVER_SIDE_ERROR + ": " + e.getMessage());
        }
        //if (feedResponse.isSuccess() && storyResponse.isSuccess())
         //   return feedResponse;
        //else
          //  return new NewStatusResponse("Could not post new status");
    }

    /**
     * Returns an instance of {}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    //public StatusesTableDAO getPostStatusDao() {
     //   return new StatusesTableDAO();
    //}

    public LanguageTableDAO getFeedTableDAO() { return new LanguageTableDAO(); }

    public DictionaryTableDAO getStoryTableDAO() { return new DictionaryTableDAO(); }

    @Override
    public NewStatusResponse postNewStatus(NewStatusRequest request) throws IOException, TweeterRemoteException {
        return null;
    }
}
