package com.example.shared.model.service;

import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.response.LoginResponse;

import java.io.IOException;


public interface ILoginService {

    public LoginResponse login(LoginRequest request) throws IOException;

    //public Response logout(LogoutRequest request) throws IOException;
}