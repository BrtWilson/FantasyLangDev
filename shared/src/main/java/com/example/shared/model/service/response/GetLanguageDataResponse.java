package com.example.shared.model.service.response;

import com.example.shared.model.domain.User;

import java.util.List;
import java.util.Objects;

/**
 * A response for a {@link com.example.shared.model.service.request.LoginRequest}.
 */
public class GetLanguageDataResponse extends Response {

    private String userName;
    private String languageName;
    private String languageID;
    private String alphabet;
    private List<String> syllableData;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public GetLanguageDataResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     */
    public GetLanguageDataResponse(String userName, String languageName, String languageID, String alphabet, List<String> syllableData) {
        super(true, null);
        this.userName = userName;
        this.languageName = languageName;
        this.languageID = languageID;
        this.alphabet = alphabet;
        this.syllableData = syllableData;
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

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public List<String> getSyllableData() {
        return syllableData;
    }

    public void setSyllableData(List<String> syllableData) {
        this.syllableData = syllableData;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        GetLanguageDataResponse that = (GetLanguageDataResponse) param;

        return (Objects.equals(userName, that.userName) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                Objects.equals(this.languageID, that.languageID) &&
                Objects.equals(this.languageName, that.languageName) &&
                Objects.equals(this.alphabet, that.alphabet) &&
                Objects.equals(this.syllableData, that.syllableData) &&
                this.isSuccess() == that.isSuccess());
    }
}