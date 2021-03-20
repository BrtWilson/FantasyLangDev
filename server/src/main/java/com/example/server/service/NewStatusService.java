package com.example.server.service;

import com.example.server.dao.StatusesTableDAO;
import com.example.shared.model.service.INewStatusService;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.response.NewStatusResponse;

import java.io.IOException;

/**
 * Contains the business logic to support the login operation.
 */
public class NewStatusService implements INewStatusService {

    public NewStatusResponse postNewStatus(NewStatusRequest request) throws IOException {
        StatusesTableDAO postStatusDAO = getPostStatusDao();
        return postStatusDAO.postNewStatus(request);
    }

    /**
     * Returns an instance of {}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    public StatusesTableDAO getPostStatusDao() {
        return new StatusesTableDAO();
    }
}
