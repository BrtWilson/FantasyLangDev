package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make a login request.
 */
public class NewStatusRequest {

    private final String userAlias;
    private final String message;
    private final String date;

    /**
     * Creates an instance.
     */
    public NewStatusRequest(String userAlias, String message, String date) {
        this.userAlias = userAlias;
        this.message = message;
        this.date = date;
    }

    /**
     * Returns the username of the user to be logged in by this request.
     *
     * @return the username.
     */
    public String getUserAlias() {
        return userAlias;
    }

    /**
     * Returns the password of the user to be logged in by this request.
     *
     * @return the password.
     */
    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}
