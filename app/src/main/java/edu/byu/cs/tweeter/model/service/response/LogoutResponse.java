package edu.byu.cs.tweeter.model.service.response;

public class LogoutResponse {

    private final String message;
    private final boolean success;

    public LogoutResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
