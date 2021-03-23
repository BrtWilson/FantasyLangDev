package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.LoginService;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.response.LoginResponse;

import java.io.IOException;

public class LogoutHandler implements RequestHandler<LoginRequest, LoginResponse> {
    @Override
    public LoginResponse handleRequest(LoginRequest loginRequest, Context context) {
        LoginService loginService = new LoginService();
        try {
            return loginService.logout(loginRequest);
        } catch (RuntimeException | IOException e) {
            String message = "[Bad Request]";
            throw new RuntimeException(message, e);
        }
    }
}
