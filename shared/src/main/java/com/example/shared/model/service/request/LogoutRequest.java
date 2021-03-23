package com.example.shared.model.service.request;

import com.example.shared.model.domain.User;

public class LogoutRequest {

    private  User user;

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
}
