package com.example.shared.model.service.request;

import com.example.shared.model.domain.DictionaryEntry;

/**
 * Contains all the information needed to make a login request.
 */
public class UpdateWordRequest {

    private String languageID;
    private DictionaryEntry fantasyWord;

    /**
     * Creates an instance.
     */
    public UpdateWordRequest(String languageID, DictionaryEntry fantasyWord) {
        this.languageID = languageID;
        this.fantasyWord = fantasyWord;
    }


    public UpdateWordRequest() {
    }

    public String getLanguageID() {
        return languageID;
    }

    public void setLanguageID(String languageID) {
        this.languageID = languageID;
    }

    public DictionaryEntry getFantasyWord() {
        return fantasyWord;
    }

    public void setFantasyWord(DictionaryEntry fantasyWord) {
        this.fantasyWord = fantasyWord;
    }

}
