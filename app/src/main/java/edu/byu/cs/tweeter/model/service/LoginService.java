package edu.byu.cs.tweeter.model.service;

import java.io.IOException;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class LoginService {

    private static ServerFacade serverFacade;
    private static LoginService instance;

    public static LoginService getInstance() {
        if(instance == null) {
            instance = new LoginService();
        }
        return instance;
    }

    public LoginResponse login(LoginRequest request) throws IOException {
        serverFacade = getServerFacade();
        LoginResponse loginResponse = serverFacade.login(request);

        if(loginResponse.isSuccess()) {
            loadImage(loginResponse.getUser());
        }

        return loginResponse;
    }

    private void loadImage(User user) throws IOException {
        byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
        user.setImageBytes(bytes);
    }

    public LogoutResponse logout(LogoutRequest request) throws IOException {
        if(serverFacade == null) {
            serverFacade = RegisterService.getServerFacade();
        }
        LogoutResponse logoutResponse = serverFacade.logout(request);
        System.out.println("logoutResponse: " + logoutResponse.getMessage());
        return logoutResponse;
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}