package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class LogoutRequest {

    private final User user;

    public LogoutRequest(User user) {
        this.user = user;
    }
}
