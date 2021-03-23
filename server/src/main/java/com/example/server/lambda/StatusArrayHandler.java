package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.StatusArrayService;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.response.StatusArrayResponse;

import java.io.IOException;

import sun.misc.Request;

public class StatusArrayHandler implements RequestHandler<StatusArrayRequest, StatusArrayResponse> {

    @Override
    public StatusArrayResponse handleRequest(StatusArrayRequest input, Context context) {
        StatusArrayService statusArrayService = new StatusArrayService();
        try {
            return (StatusArrayResponse) statusArrayService.getList(input);
        } catch (RuntimeException e) {
            String message = "[Bad Request]";
            throw new RuntimeException(message, e);
        }

    }
}
