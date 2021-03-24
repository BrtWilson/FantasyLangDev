package edu.byu.cs.client.model.service;

import java.io.IOException;
import com.example.shared.model.domain.User;
import edu.byu.cs.client.model.net.ServerFacade;

import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.ILoginService;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.BasicResponse;
import edu.byu.cs.client.util.ByteArrayUtils;

public class LoginService implements ILoginService {

    private static ServerFacade serverFacade;
    private static LoginService instance;

    public static LoginService getInstance() {
        if(instance == null) {
            instance = new LoginService();
        }
        return instance;
    }

    public LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException, RuntimeException {
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

    public BasicResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException {
        if(serverFacade == null) {
            serverFacade = RegisterService.getServerFacade();
        }
        BasicResponse logoutResponse = serverFacade.logout(request);
        System.out.println("logoutResponse: " + logoutResponse.getMessage());
        return logoutResponse;
    }

    ServerFacade getServerFacade() {
        if (serverFacade == null) {
            serverFacade = new ServerFacade();
        }
        return serverFacade;
    }
}