package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.RegisterService;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.RegisterResponse;

import java.io.IOException;

public class RegisterHandler implements RequestHandler<RegisterRequest, RegisterResponse> {
    @Override
    public RegisterResponse handleRequest(RegisterRequest registerRequest, Context context) {
        RegisterService registerService = new RegisterService();
        try {
            return registerService.register(registerRequest);
        } catch (IOException e) {
            System.out.println("Error: 400 ");
            e.printStackTrace();        }
        return null;
    }
}
