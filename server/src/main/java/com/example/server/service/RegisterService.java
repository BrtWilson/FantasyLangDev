package com.example.server.service;

import com.example.server.dao.UsersTableDAO;
import com.example.shared.model.service.IRegisterService;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.RegisterResponse;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Base64;

public class RegisterService implements IRegisterService {

    public RegisterResponse register(RegisterRequest request) throws IOException {
        PasswordHasher passwordHasher = new PasswordHasher();
        String hashedPassword = passwordHasher.hashPassword(request.getPassword());
        request.setPassword(hashedPassword);

        RegisterResponse registerResponse = getRegisterDao().register(request);

        return registerResponse;
    }

    public static UsersTableDAO getRegisterDao() {
        return new UsersTableDAO();
    }
}