package com.example.shared.model.service.response;

import com.example.shared.model.domain.User;

import java.util.List;
import java.util.Objects;

/**
 * A response for a {@link com.example.shared.model.service.request.LoginRequest}.
 */
public class LoginResponse extends Response {

    private User user;
    private List<String> userLanguages;

    /**
     * Creates a response indicating that the corresponding request was NOT SUCCESSFUL.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public LoginResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was SUCCESSFUL.
     *
     * @param user the now logged in user.
     */
    public LoginResponse(User user, List<String> userLanguages) {
        super(true, null);
        this.user = user;
        this.userLanguages = userLanguages;
    }

    /**
     * Returns the logged in user.
     *
     * @return the user.
     */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

        LoginResponse that = (LoginResponse) param;

        return (Objects.equals(user, that.user) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }
}
