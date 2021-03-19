package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.response.LoginResponse;
import com.example.server.service.LoginService;

import java.io.IOException;

//TODO: These Handlers

/**
 * An AWS lambda function that logs a user in and returns the user object and an auth code for
 * a successful login.
 */
public class LoginHandler implements RequestHandler<LoginRequest, LoginResponse> {
    @Override
    public LoginResponse handleRequest(LoginRequest loginRequest, Context context) {
        LoginService loginService = new LoginService();
        try {
            return loginService.login(loginRequest);
        } catch (IOException e) {
            System.out.println("Error: 400 ");
            e.printStackTrace();
        }
        return null;
    }
}
