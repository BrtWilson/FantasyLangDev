package com.example.server.service;

import com.example.server.dao.LanguageTableDAO;
import com.example.server.dao.DictionaryTableDAO;
import com.example.shared.model.service.IStatusArrayService;
import com.example.shared.model.service.request.IListRequest;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.response.IListResponse;
import com.example.shared.model.service.response.StatusArrayResponse;

/**
 * Contains the business logic for getting a user's feed or story
 */
public class StatusArrayService implements IStatusArrayService {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {} to
     * get the followees from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public StatusArrayResponse requestStatusArray(IListRequest request) {
        if (request.getClass() != StatusArrayRequest.class) {
            return new StatusArrayResponse("Statuses missing");
        }
        StatusArrayRequest req = (StatusArrayRequest) request;
        //response = getStatusArrayDao().getStatusArray(req);

        if (req.getFeedInstead())
           return getFeedTableDAO().getStatusArray(req);
        else
           return getStoryTableDAO().getStatusArray(req);
    }

    @Override
    public IListResponse getList(IListRequest listRequest) {
        return requestStatusArray(listRequest);
    }


    /**
     * Returns an instance of {}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    //public StatusesTableDAO getStatusArrayDao() {
      //  return new StatusesTableDAO();
    //}

    public LanguageTableDAO getFeedTableDAO() { return new LanguageTableDAO(); }
    public DictionaryTableDAO getStoryTableDAO() { return new DictionaryTableDAO(); }

}
