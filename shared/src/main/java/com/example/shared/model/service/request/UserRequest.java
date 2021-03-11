package com.example.shared.model.service.request;

/**
 * Contains all the information needed to make a login request.
 */
public class UserRequest {

    private final String alias;

    /**
     * Creates an instance.
     *
     * @param username the username of the user to be logged in.
     */
    public UserRequest(String username) {
        this.alias = username;
    }

    /**
     * Returns the username of the user to be logged in by this request.
     *
     * @return the username.
     */
    public String getAlias() {
        return alias;
    }

}