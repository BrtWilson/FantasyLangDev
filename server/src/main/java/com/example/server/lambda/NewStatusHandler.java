package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.NewStatusService;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.response.NewStatusResponse;

import java.io.IOException;

public class NewStatusHandler implements RequestHandler<NewStatusRequest, NewStatusResponse> {
    @Override
    public NewStatusResponse handleRequest(NewStatusRequest newStatusRequest, Context context) {
        NewStatusService newStatusService = new NewStatusService();
        try {
            return newStatusService.postNewStatus(newStatusRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}