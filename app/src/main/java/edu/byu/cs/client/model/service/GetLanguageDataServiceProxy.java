package edu.byu.cs.client.model.service;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.IGetLanguageDataService;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.response.GetLanguageDataResponse;

import java.io.IOException;

import edu.byu.cs.client.model.net.ServerFacade;

public class GetLanguageDataServiceProxy implements IGetLanguageDataService {
    @Override
    public GetLanguageDataResponse getLanguageData(GetLanguageDataRequest request) throws IOException, RemoteException {
        return getServerFacade().getLanguageData(request);
    }

    ServerFacade getServerFacade() { return new ServerFacade(); }
}
