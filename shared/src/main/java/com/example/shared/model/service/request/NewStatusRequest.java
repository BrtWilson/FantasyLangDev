package com.example.shared.model.service.request;

import com.example.shared.model.domain.User;

/**
 * Contains all the information needed to make a login request.
 */
public class NewStatusRequest {

    private  String userAlias;
    private  String message;
    private  String date;

    /**
     * Creates an instance.
     */
    public NewStatusRequest(String userAlias, String message, String date) {
        this.userAlias = userAlias;
        this.message = message;
        this.date = date;
    }

    public NewStatusRequest() {
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

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
