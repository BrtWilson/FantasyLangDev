package com.example.server.service;

import com.example.server.dao.LoginDAO;
import com.example.server.dao.LogoutDAO;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.ILoginService;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.LogoutResponse;

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

    public LogoutResponse logout(LogoutRequest request) throws IOException {
        LogoutResponse logoutResponse = getLogoutDao().logout(request);
        System.out.println("logoutResponse: " + logoutResponse.getMessage());
        return logoutResponse;
    }

    LoginDAO getLoginDao() {
        return new LoginDAO();
    }

    LogoutDAO getLogoutDao() {
        return new LogoutDAO();
    }
}