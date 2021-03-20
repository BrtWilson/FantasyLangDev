package com.example.shared.model.service.response;

import com.example.shared.model.domain.Status;

import java.util.Objects;

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

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        NewStatusResponse that = (NewStatusResponse) param;

        return (Objects.equals(newStatus, that.getNewStatus()) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }
}