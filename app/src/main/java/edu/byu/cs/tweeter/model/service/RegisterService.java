package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterService {

    private static RegisterService instance;
    private final ServerFacade serverFacade;

    public static RegisterService getInstance() {
        if (instance == null) {
            instance = new RegisterService();
        }

        return instance;
    }

    public RegisterService() {
        serverFacade = new ServerFacade();
    }

    public RegisterResponse register(RegisterRequest request) {
        return serverFacade.register(request);
    }
}