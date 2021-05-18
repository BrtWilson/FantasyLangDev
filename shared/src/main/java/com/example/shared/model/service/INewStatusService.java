package com.example.shared.model.service;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.NewLanguageRequest;
import com.example.shared.model.service.response.NewLanguageResponse;

import java.io.IOException;

/**
 * Contains the business logic to support the login operation.
 */
public interface INewStatusService {

    public NewLanguageResponse postNewStatus(NewLanguageRequest request) throws IOException, RemoteException;
}
