package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.StatusArrayService;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.response.StatusArrayResponse;

import sun.misc.Request;

public class StatusArrayHandler implements RequestHandler<StatusArrayRequest, StatusArrayResponse> {

    @Override
    public StatusArrayResponse handleRequest(StatusArrayRequest input, Context context) {
        StatusArrayService statusArrayService = new StatusArrayService();
        //return statusArrayService.getList(input, )
        return null;
    }
}
