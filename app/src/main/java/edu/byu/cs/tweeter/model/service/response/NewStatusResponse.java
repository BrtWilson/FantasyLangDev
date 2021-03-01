package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.Status;

public class NewStatusResponse extends Response {

    private Status newStatus;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public NewStatusResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     */
    public NewStatusResponse(Status newStatus) {
        super(true, null);
        this.newStatus = newStatus;
    }

    public Status getNewStatus() {
        return newStatus;
    }
}