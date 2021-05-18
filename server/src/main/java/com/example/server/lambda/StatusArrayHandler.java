package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.StatusArrayService;
import com.example.shared.model.service.request.UpdateSyllablesRequest;
import com.example.shared.model.service.response.DictionaryPageResponse;

import java.util.Arrays;

public class StatusArrayHandler implements RequestHandler<UpdateSyllablesRequest, DictionaryPageResponse> {

    @Override
    public DictionaryPageResponse handleRequest(UpdateSyllablesRequest input, Context context) {
        StatusArrayService statusArrayService = new StatusArrayService();
        try {
            return (DictionaryPageResponse) statusArrayService.getList(input);
        } catch (RuntimeException e) {
            String message = "[Bad Request] : " + Arrays.toString(e.getStackTrace());
            throw new RuntimeException(message);
        }

    }
}
