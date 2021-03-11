package edu.byu.cs.client.model.service;

import java.io.IOException;

import com.example.shared.model.domain.User;
import edu.byu.cs.client.model.net.ServerFacade;

import com.example.shared.model.service.INewStatusService;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.response.NewStatusResponse;

/**
 * Contains the business logic to support the login operation.
 */
public class NewStatusService implements INewStatusService {

    static final String URL_PATH = "/poststatus";

    public NewStatusResponse postNewStatus(NewStatusRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        return serverFacade.pushNewStatus(request, URL_PATH);
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
