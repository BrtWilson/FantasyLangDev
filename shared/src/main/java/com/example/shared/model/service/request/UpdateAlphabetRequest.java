package com.example.shared.model.service.request;

/**
 * Contains all the information needed to make a login request.
 */
public class UpdateAlphabetRequest {

    private String userName;
    private String languageName;
    private String languageID;
    private String alphabet;

    /**
     * Creates an instance.
     */
    public UpdateAlphabetRequest(String userName, String languageName, String languageID, String alphabet) {
        this.userName = userName;
        this.languageName = languageName;
        this.languageID = languageID;
        this.alphabet = alphabet;
    }

    public UpdateAlphabetRequest() {
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
}
