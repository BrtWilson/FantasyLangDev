package com.example.shared.model.service.request;

public class LogoutRequest {

    private String user;
    private AuthToken token;

    //TODO:  Add AuthToken to logout
    public LogoutRequest(String user, AuthToken token) {
        this.user = user;
        this.token = token;
    }

    public LogoutRequest(String user) {
        this.user = user;
    }

    public LogoutRequest() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public AuthToken getToken() {
        return token;
    }

    public void setToken(AuthToken token) {
        this.token = token;
    }
}
