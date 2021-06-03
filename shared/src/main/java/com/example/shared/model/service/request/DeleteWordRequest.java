package com.example.shared.model.service.request;

import com.example.shared.model.domain.Dictionary;

/**
 * Contains all the information needed to make a login request.
 */
public class DeleteWordRequest {

    private String languageID;
    private Dictionary fantasyWord;

    /**
     * Creates an instance.
     */
    public DeleteWordRequest(String languageID, Dictionary fantasyWord) {
        this.languageID = languageID;
        this.fantasyWord = fantasyWord;
    }

    public DeleteWordRequest() {
    }

    public String getLanguageID() {
        return languageID;
    }

    public void setLanguageID(String languageID) {
        this.languageID = languageID;
    }

    public Dictionary getFantasyWord() {
        return fantasyWord;
    }

    public void setFantasyWord(Dictionary fantasyWord) {
        this.fantasyWord = fantasyWord;
    }

}
