package com.example.shared.model.service.response;

import java.util.Objects;

public class GeneralUpdateResponse extends Response {

    private String languageID;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public GeneralUpdateResponse(boolean success, String message) {
        super(success, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     */
    public GeneralUpdateResponse(String languageID) {
        super(true, null);
        this.languageID = languageID;
    }

    public String getLanguageID() {
        return languageID;
    }

    public void setLanguageID(String languageID) {
        this.languageID = languageID;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        GeneralUpdateResponse that = (GeneralUpdateResponse) param;

        return (Objects.equals(this.languageID, that.languageID) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }
}