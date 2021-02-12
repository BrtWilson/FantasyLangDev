package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followees for a specified follower.
 */
public class NewStatusRequest {

    private final String statusMessage;
    private final String date;
    private final User correspondingUser;

    public NewStatusRequest(String statusMessage, User correspondingUser, String date) {
        this.statusMessage = statusMessage;
        this.correspondingUser = correspondingUser;
        this.date = date;
    }

    public String getStatusMessage() {
        return statusMessage;
    }


    public User getCorrespondingUser() {
        return correspondingUser;
    }


    public String getDate() {
        return date;
    }
}
