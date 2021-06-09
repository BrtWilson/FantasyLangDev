package edu.byu.cs.client.model.service;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.IUserService;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.RegisterResponse;

import java.io.IOException;

import edu.byu.cs.client.model.net.ServerFacade;

public class SignUpServiceProxy implements IUserService {
    private static final String URL_PATH = "/signup";

    @Override
    public LoginResponse login(LoginRequest request) throws IOException {
        return null;
    }

    public RegisterResponse register(RegisterRequest request) throws IOException, RemoteException {
        ServerFacade serverFacade = getServerFacade();
        RegisterResponse response = serverFacade.register(request);

        return response;
    }

    ServerFacade getServerFacade() { return new ServerFacade(); }
}
