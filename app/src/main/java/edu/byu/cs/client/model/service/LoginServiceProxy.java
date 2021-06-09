package edu.byu.cs.client.model.service;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.IUserService;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.RegisterResponse;

import java.io.IOException;

import edu.byu.cs.client.model.net.ServerFacade;

public class LoginServiceProxy implements IUserService {
    private static final String URL_PATH = "/login";

    public LoginResponse login(LoginRequest request) throws IOException, RemoteException {
        ServerFacade serverFacade = getServerFacade();
        LoginResponse response = serverFacade.login(request);

        return response;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) throws IOException {
        return null;
    }

    ServerFacade getServerFacade() { return new ServerFacade(); }
}
