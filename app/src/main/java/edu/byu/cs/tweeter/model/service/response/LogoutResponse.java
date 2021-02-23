package edu.byu.cs.tweeter.model.service.response;

public class LogoutResponse {

    private final String message;

    public LogoutResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
