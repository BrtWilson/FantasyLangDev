package com.example.shared.model.service;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.RegisterResponse;

import java.io.IOException;

public interface IUserService {

    public LoginResponse login(LoginRequest request) throws IOException;
    public RegisterResponse register(RegisterRequest request) throws IOException;
}