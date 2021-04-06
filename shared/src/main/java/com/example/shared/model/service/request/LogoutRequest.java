package com.example.shared.model.service.request;

import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;

public class LogoutRequest {

    private  User user;
    private AuthToken token;

    //TODO:  Add AuthToken to logout
    public LogoutRequest(User user, AuthToken token) {
        this.user = user;
        this.token = token;
    }

    public LogoutRequest(User user) {
        this.user = user;
    }

    public LogoutRequest() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuthToken getToken() {
        return token;
    }

    public void setToken(AuthToken token) {
        this.token = token;
    }
}
