package com.example.shared.model.service;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.UpdateSyllablesRequest;
import com.example.shared.model.service.response.GeneralUpdateResponse;

import java.io.IOException;

public interface IUpdateSyllableService {
    public GeneralUpdateResponse updateSyllables(UpdateSyllablesRequest request) throws IOException, RemoteException;
}
