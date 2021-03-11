package com.example.shared.model.service.request;

import com.example.shared.model.domain.User;

public class LogoutRequest {

    private final User user;

    public LogoutRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
