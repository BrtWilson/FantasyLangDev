package com.example.shared.model.service.response;

import java.util.Objects;

public class NewLanguageResponse extends Response {

    private String userName;
    private String languageName;
    private String languageID;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public NewLanguageResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     */
    public NewLanguageResponse(String userName, String languageName, String languageID) {
        super(true, null);
        this.userName = userName;
        this.languageName = languageName;
        this.languageID = languageID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
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

        NewLanguageResponse that = (NewLanguageResponse) param;

        return (Objects.equals(this.userName, that.userName) &&
                Objects.equals(this.languageName, that.languageName) &&
                Objects.equals(this.languageID, that.languageID) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }
}