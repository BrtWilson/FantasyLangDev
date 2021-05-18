package com.example.shared.model.service.request;

/**
 * Contains all the information needed to make a login request.
 */
public class TranslateRequest {

    private String searchByData;
    private String languageID;
    private boolean toFantasyLang;

    /**
     *
     * @param searchByData the string entered by user to find corresponding words
     * @param languageID the id of the language being searched
     * @param toFantasyLang whether the translation request is to or from the fantasy language
     */
    public TranslateRequest(String searchByData, String languageID, boolean toFantasyLang) {
        this.searchByData = searchByData;
        this.languageID = languageID;
        this.toFantasyLang = toFantasyLang;
    }

    public TranslateRequest() {
    }

    public String getSearchByData() {
        return searchByData;
    }

    public void setSearchByData(String searchByData) {
        this.searchByData = searchByData;
    }

    public String getLanguageID() {
        return languageID;
    }

    public void setLanguageID(String languageID) {
        this.languageID = languageID;
    }

    public boolean isToFantasyLang() {
        return toFantasyLang;
    }

    public void setToFantasyLang(boolean toFantasyLang) {
        this.toFantasyLang = toFantasyLang;
    }
}
