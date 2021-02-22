package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class RegisterService {
    public RegisterResponse register(RegisterRequest request) throws IOException {
        ServerFacade serverFacade = getSerferFacade();
        RegisterResponse registerResponse = serverFacade.register(request);

        if(registerResponse.isSuccess()) {
            // TODO: check load Image?
            loadImage(registerResponse.getUser());
            System.out.println("Register Service: Register is success.");
        }

        return registerResponse;
    }

    private void loadImage(User user) throws IOException {
        byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
        user.setImageBytes(bytes);
    }

    ServerFacade getSerferFacade() { return new ServerFacade();}
}