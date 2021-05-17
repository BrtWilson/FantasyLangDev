package com.example.shared.model.service.response;

import com.example.shared.model.domain.User;

import java.util.Objects;

/**
 * A response for a {@link com.example.shared.model.service.request.LoginRequest}.
 */
public class LoginResponse extends Response {

    private User user;

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
    public LoginResponse(User user) {
        super(true, null);
        this.user = user;
    }

    /**
     * Returns the logged in user.
     *
     * @return the user.
     */
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

        LoginResponse that = (LoginResponse) param;

        return (Objects.equals(user, that.user) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }
}
