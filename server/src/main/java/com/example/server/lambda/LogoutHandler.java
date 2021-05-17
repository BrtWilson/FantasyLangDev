package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.LoginService;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.response.Response;

import java.io.IOException;

public class LogoutHandler implements RequestHandler<LogoutRequest, Response> {
    @Override
    public Response handleRequest(LogoutRequest logoutRequest, Context context) {
        LoginService loginService = new LoginService();
        try {
            return loginService.logout(logoutRequest);
        } catch (RuntimeException | IOException e) {
            String message = "[Bad Request]";
            throw new RuntimeException(message, e);
        }
    }
}
