package com.example.shared.model.service;

import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.RegisterResponse;

import java.io.IOException;

public interface IRegisterService {

    public RegisterResponse register(RegisterRequest request) throws IOException;
}