package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterResponse {

    private final User user;
    private final AuthToken authToken;
    private final String message;
    private final boolean success;

    public RegisterResponse(User user, AuthToken authToken, boolean success) {
        this.user = user;
        this.authToken = authToken;
        this.success = success;
        message = "";
    }

    public RegisterResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
        this.user = null;
        this.authToken = null;
    }

    public User getUser() {
        return user;
    }
    public AuthToken getAuthToken() {
        return authToken;
    }
    public String getMessage() {
        return message;
    }
    public boolean isSuccess() { return success; }
}