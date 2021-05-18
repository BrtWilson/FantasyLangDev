package com.example.shared.model.service.response;

import com.example.shared.model.domain.User;

import java.util.List;
import java.util.Objects;

public class RegisterResponse extends Response{

    private final User user;
    private List<String> userLanguages;

    /**
     * If response is SUCCESSFUL
     * @param user User object created from successful registration
     * @param userLanguages List of Strings of languageIDs
     */
    public RegisterResponse(User user, List<String> userLanguages) {
        super(true);
        this.user = user;
        this.userLanguages = userLanguages;
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

    public List<String> getUserLanguages() {
        return userLanguages;
    }

    public void setUserLanguages(List<String> userLanguages) {
        this.userLanguages = userLanguages;
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