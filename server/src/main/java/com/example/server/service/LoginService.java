package com.example.server.service;

import com.example.server.dao.UsersTableDAO;
import com.example.shared.model.service.ILoginService;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.Response;

import java.io.IOException;

public class LoginService implements ILoginService {

    private static LoginService instance;

    public static LoginService getInstance() {
        if(instance == null) {
            instance = new LoginService();
        }
        return instance;
    }

    public LoginResponse login(LoginRequest request) throws IOException {
        //PasswordHasher passwordHasher = new PasswordHasher();
        //String hashedPassword = passwordHasher.hashPassword(request.getPassword());
       // request.setPassword(hashedPassword);

        return getLoginDao().login(request);
    }

    public Response logout(LogoutRequest request) throws IOException {
        Response logoutResponse = getLoginDao().logout(request);
        System.out.println("logoutResponse: " + logoutResponse.getMessage());
        return logoutResponse;
    }

    public UsersTableDAO getLoginDao() {
        return new UsersTableDAO();
    }
}