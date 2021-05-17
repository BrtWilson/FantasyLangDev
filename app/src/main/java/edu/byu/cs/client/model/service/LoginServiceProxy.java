package edu.byu.cs.client.model.service;

import com.example.shared.model.service.ILoginService;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.response.LoginResponse;

import java.io.IOException;

import edu.byu.cs.client.model.net.ServerFacade;

public class LoginServiceProxy implements ILoginService {
    private static final String URL_PATH = "/login";

    public LoginResponse login(LoginRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        LoginResponse response = serverFacade.login(request);

        return response;
    }

    ServerFacade getServerFacade() { return new ServerFacade(); }
}
