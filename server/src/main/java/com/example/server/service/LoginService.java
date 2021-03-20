package com.example.server.service;

import com.example.server.dao.UsersTableDAO;
import com.example.shared.model.service.ILoginService;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.BasicResponse;

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
        LoginResponse loginResponse = getLoginDao().login(request);

        return loginResponse;
    }

    public BasicResponse logout(LogoutRequest request) throws IOException {
        BasicResponse logoutResponse = getLoginDao().logout(request);
        System.out.println("logoutResponse: " + logoutResponse.getMessage());
        return logoutResponse;
    }

    public UsersTableDAO getLoginDao() {
        return new UsersTableDAO();
    }
}