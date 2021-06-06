package com.example.shared.model.service.request;

import java.util.Map;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followees for a specified follower.
 */
public class UpdateSyllablesRequest {//extends ListRequest{

    private String languageID;
    private String syllableTemplate;
    private Map<Integer, String> syllables;

    public UpdateSyllablesRequest(String languageID, String syllableTemplate, Map<Integer, String> syllables) {
        this.languageID = languageID;
        this.syllableTemplate = syllableTemplate;
        this.syllables = syllables;
    }

    public String getLanguageID() {
        return languageID;
    }

    public void setLanguageID(String languageID) {
        this.languageID = languageID;
    }

    public String getSyllableTemplate() {
        return syllableTemplate;
    }

    public void setSyllableTemplate(String syllableTemplate) {
        this.syllableTemplate = syllableTemplate;
    }

    public Map<Integer, String> getSyllables() {
        return syllables;
    }

    public void setSyllables(Map<Integer, String> syllables) {
        this.syllables = syllables;
    }
}
