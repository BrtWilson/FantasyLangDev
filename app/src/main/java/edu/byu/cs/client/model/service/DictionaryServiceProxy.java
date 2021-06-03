package edu.byu.cs.client.model.service;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.IDictionaryService;
import com.example.shared.model.service.request.DictionaryPageRequest;
import com.example.shared.model.service.response.DictionaryPageResponse;

import java.io.IOException;

import edu.byu.cs.client.model.net.ServerFacade;

public class DictionaryServiceProxy implements IDictionaryService {
    private static final String URL_PATH = "/dictionary";

    public DictionaryPageResponse dictionary(DictionaryPageRequest request) throws IOException, RemoteException {
        ServerFacade serverFacade = getServerFacade();
        DictionaryPageResponse response = serverFacade.dictionary(request);//, URL_PATH);

        return response;
    }

    ServerFacade getServerFacade() { return new ServerFacade(); }
}
