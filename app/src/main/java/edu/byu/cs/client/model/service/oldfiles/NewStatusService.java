package edu.byu.cs.client.model.service.oldfiles;

import java.io.IOException;

import edu.byu.cs.client.model.net.ServerFacade;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.INewStatusService;
import com.example.shared.model.service.request.NewLanguageRequest;
import com.example.shared.model.service.response.NewLanguageResponse;

/**
 * Contains the business logic to support the login operation.
 */
public class NewStatusService implements INewStatusService {


    public NewLanguageResponse postNewStatus(NewLanguageRequest request) throws IOException, RemoteException {
        ServerFacade serverFacade = getServerFacade();
        return serverFacade.pushNewStatus(request);
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
