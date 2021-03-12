package com.example.server.service;

import com.example.server.dao.StatusArrayDAO;
import com.example.shared.model.service.IStatusArrayService;
import com.example.shared.model.service.IStatuses_Observer;
import com.example.shared.model.service.request.IListRequest;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.response.IListResponse;
import com.example.shared.model.service.response.StatusArrayResponse;

import java.io.IOException;

/**
 * Contains the business logic for getting the users a user is following.
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
    public StatusArrayResponse requestStatusArray(IListRequest request, IStatuses_Observer statuses_observer) {
        StatusArrayResponse response = new StatusArrayResponse("Statuses missing");
        //try {
            if (request.getClass() != StatusArrayRequest.class) {
                return response;
            }
            StatusArrayRequest req = (StatusArrayRequest) request;
            response = getStatusArrayDao().getStatusArray(req, statuses_observer);

        /*} catch (IOException e) {
            response = new StatusArrayResponse("Statuses missing error");
            return response;
        }*/

        return response;
    }

    @Override
    public IListResponse getList(IListRequest listRequest, IStatuses_Observer statuses_observer) {
        return requestStatusArray(listRequest, statuses_observer);
    }


    /**
     * Returns an instance of {}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    StatusArrayDAO getStatusArrayDao() {
        return new StatusArrayDAO();
    }

}
