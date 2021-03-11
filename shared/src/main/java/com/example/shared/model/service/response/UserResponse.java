package com.example.shared.model.service.response;

import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;

/**
 * A response for a {@link com.example.shared.model.service.request.LoginRequest}.
 */
public class UserResponse extends Response {

    private User user;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public UserResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param user the now logged in user.
     */
    public UserResponse(User user) {
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
}