package com.example.shared.model.service.request;

/**
 * Contains all the information needed to make a login request.
 */
public class GetLanguageDataRequest {

    private  String languageID;

    /**
     * Creates an instance.
     *
     * @param langID the username of the user to be logged in.
     */
    public GetLanguageDataRequest(String langID) {
        this.languageID = langID;
    }

    /**
     * Returns the username of the user to be logged in by this request.
     *
     * @return the username.
     */
    public String getLanguageID() {
        return languageID;
    }

    public GetLanguageDataRequest() {
    }

    public void setLanguageID(String languageID) {
        this.languageID = languageID;
    }
}