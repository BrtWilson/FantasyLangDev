package com.example.shared.model.service;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.RegisterResponse;

import java.io.IOException;

public interface IRegisterService {

    public RegisterResponse signUp(RegisterRequest request) throws IOException;
}