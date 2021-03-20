package com.example.shared.model.service.response;

import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;

import java.util.Objects;

public class RegisterResponse extends Response{

    private final User user;
    private final AuthToken authToken;

    public RegisterResponse(User user, AuthToken authToken, boolean success) {
        super(success);
        this.user = user;
        this.authToken = authToken;
    }

    public RegisterResponse(String message, boolean success) {
        super(success,message);
        this.user = null;
        this.authToken = null;
    }

    public User getUser() {
        return user;
    }
    public AuthToken getAuthToken() {
        return authToken;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        RegisterResponse that = (RegisterResponse) param;

        return (Objects.equals(user, that.user) &&
                Objects.equals(authToken, that.authToken) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }
}