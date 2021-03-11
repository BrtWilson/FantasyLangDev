package com.example.server.service;

import com.example.server.dao.RegisterDAO;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.IRegisterService;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.RegisterResponse;

import java.io.IOException;

public class RegisterService implements IRegisterService {

    public RegisterResponse register(RegisterRequest request) throws IOException {
        RegisterResponse registerResponse = getRegisterDao().register(request);

        return registerResponse;
    }

    static RegisterDAO getRegisterDao() {
        return new RegisterDAO();
    }
}