package com.example.shared.model.service.response;

import java.util.Objects;

public class NewWordResponse extends Response {

    private String languageID;
    private String fantasyWord;
    private Boolean needsConfirmation;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public NewWordResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     */
    public NewWordResponse(String languageID, String fantasyWord, Boolean needsConfirmation) {
        super(true, null);
        this.languageID = languageID;
        this.fantasyWord = fantasyWord;
        this.needsConfirmation = needsConfirmation;
    }

    public String getFantasyWord() {
        return fantasyWord;
    }

    public void setFantasyWord(String fantasyWord) {
        this.fantasyWord = fantasyWord;
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

        NewWordResponse that = (NewWordResponse) param;

        return (Objects.equals(this.needsConfirmation, that.needsConfirmation) &&
                Objects.equals(this.fantasyWord, that.fantasyWord) &&
                Objects.equals(this.languageID, that.languageID) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }
}