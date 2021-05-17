package edu.byu.cs.client.model.service;

import com.example.shared.model.service.IRegisterService;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.RegisterResponse;

import java.io.IOException;

import edu.byu.cs.client.model.net.ServerFacade;

public class SignUpServiceProxy implements IRegisterService {
    private static final String URL_PATH = "/signup";

    public RegisterResponse signUp(RegisterRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        RegisterResponse response = serverFacade.register(request);

        return response;
    }

    ServerFacade getServerFacade() { return new ServerFacade(); }
}
