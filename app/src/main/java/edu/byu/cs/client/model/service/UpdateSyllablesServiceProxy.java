package edu.byu.cs.client.model.service;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.IUpdateSyllableService;
import com.example.shared.model.service.request.UpdateSyllablesRequest;
import com.example.shared.model.service.response.GeneralUpdateResponse;

import java.io.IOException;

import edu.byu.cs.client.model.net.ServerFacade;

public class UpdateSyllablesServiceProxy implements IUpdateSyllableService {
    @Override
    public GeneralUpdateResponse updateSyllables(UpdateSyllablesRequest request) throws IOException, RemoteException {
        return getServerFacade().updateSyllables(request);
    }

    ServerFacade getServerFacade() { return new ServerFacade(); }
}
