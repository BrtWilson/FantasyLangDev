package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.UserService;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.response.GetLanguageDataResponse;

import java.io.IOException;

public class UserByAliasHandler implements RequestHandler<GetLanguageDataRequest, GetLanguageDataResponse> {

    @Override
    public GetLanguageDataResponse handleRequest(GetLanguageDataRequest getLanguageDataRequest, Context context) {
        UserService userService = new UserService();
        try {
            userService.getUserByAlias(getLanguageDataRequest);
        } catch (RuntimeException | IOException e) {
            String message = "[Bad Request]";
            throw new RuntimeException(message, e);
        }
        return null;
    }
}
