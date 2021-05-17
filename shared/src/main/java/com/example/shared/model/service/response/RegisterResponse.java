package com.example.shared.model.service.response;

import com.example.shared.model.domain.User;

import java.util.Objects;

public class RegisterResponse extends Response{

    private final User user;

    /**
     * If response is SUCCESSFUL
     * @param user User object created from successful registration
     * @param success boolean of successfulness
     */
    public RegisterResponse(User user, boolean success) {
        super(success);
        this.user = user;
    }

    /**
     * If response is NOT SUCCESSFUL
     * @param message error message
     * @param success boolean of successfulness
     */
    public RegisterResponse(String message, boolean success) {
        super(success, message);
        this.user = null;
    }

    public User getUser() {
        return user;
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
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }
}