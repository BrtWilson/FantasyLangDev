package edu.byu.cs.client.model.service;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.IUpdateAlphabet;
import com.example.shared.model.service.request.UpdateAlphabetRequest;
import com.example.shared.model.service.response.GeneralUpdateResponse;

import java.io.IOException;

import edu.byu.cs.client.model.net.ServerFacade;

public class UpdateAlphabetServiceProxy implements IUpdateAlphabet {
    @Override
    public GeneralUpdateResponse updateAlphabet(UpdateAlphabetRequest request) throws IOException, RemoteException {
        return getServerFacade().updateAlphabet(request);
    }

    ServerFacade getServerFacade() { return new ServerFacade(); }
}
