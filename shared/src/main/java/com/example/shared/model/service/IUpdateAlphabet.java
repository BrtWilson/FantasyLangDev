package com.example.shared.model.service;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.UpdateAlphabetRequest;
import com.example.shared.model.service.response.GeneralUpdateResponse;

import java.io.IOException;

public interface IUpdateAlphabet {
    public GeneralUpdateResponse updateAlphabet(UpdateAlphabetRequest request) throws IOException, RemoteException;
}
