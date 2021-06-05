package edu.byu.cs.client.model.service;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.INewLanguageService;
import com.example.shared.model.service.request.NewLanguageRequest;
import com.example.shared.model.service.response.NewLanguageResponse;

import java.io.IOException;

import edu.byu.cs.client.model.net.ServerFacade;

public class NewLanguageServiceProxy implements INewLanguageService {
    public NewLanguageResponse newLanguage(NewLanguageRequest request) throws IOException, RemoteException {
        ServerFacade serverFacade = getServerFacade();
        NewLanguageResponse response = serverFacade.createNewLanguage(request);

        return response;
    }

    ServerFacade getServerFacade() { return new ServerFacade(); }
}
