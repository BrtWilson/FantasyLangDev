package com.example.shared.model.service;

import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.response.NewStatusResponse;

import java.io.IOException;

/**
 * Contains the business logic to support the login operation.
 */
public interface INewStatusService {

    public NewStatusResponse postNewStatus(NewStatusRequest request) throws IOException;
}
