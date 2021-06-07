package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.UserService;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.GetLanguageDataResponse;
import com.example.shared.model.service.response.RegisterResponse;

import java.io.IOException;

public class UserRegisterHandler implements RequestHandler<RegisterRequest, RegisterResponse> {

    @Override
    public RegisterResponse handleRequest(RegisterRequest registerRequest, Context context) {
        UserService userService = new UserService();
        RegisterResponse response = null;
        try {
            response = userService.register(registerRequest);
        } catch (RuntimeException | IOException e) {
            String message = "[Bad Request]";
            throw new RuntimeException(message, e);
        }
        return response;
    }
}
