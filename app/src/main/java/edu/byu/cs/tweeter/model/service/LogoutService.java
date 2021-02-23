package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutService {
    public LogoutResponse logout(LogoutRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        LogoutResponse logoutResponse = serverFacade.logout(request);

        if(logoutResponse.isSuccess()) {
            System.out.println("Logout Service: Logout is successful.");
        }

        return logoutResponse;
    }

    ServerFacade getServerFacade() { return new ServerFacade(); }
}
