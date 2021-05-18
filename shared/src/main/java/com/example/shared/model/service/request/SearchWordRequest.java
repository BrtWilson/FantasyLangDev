package com.example.shared.model.service.request;

/**
 * Contains all the information needed to make a login request.
 */
public class SearchWordRequest {

    public final int BY_UNSPECIFIED = 0;
    public final int BY_TRANSLATION = 1;
    public final int BY_FANTASYWORD = 2;
    public final String str_BY_UNSPECIFIED = "Unspecified";
    public final String str_BY_TRANSLATION = "Translation";
    public final String str_BY_FANTASYWORD = "FantasyWord";

    private String searchByData;
    private String languageID;
    private int typeOfData;

    /**
     *
     * @param searchByData the string entered by user to find corresponding words
     * @param languageID the id of the language being searched
     * @param typeOfData probably "Translation"
     */
    public SearchWordRequest(String searchByData, String languageID, int typeOfData) {
        this.searchByData = searchByData;
        this.languageID = languageID;
        this.typeOfData = typeOfData;
    }

    public SearchWordRequest() {
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

    public String getTypeOfData() {
        if (typeOfData == BY_UNSPECIFIED) return str_BY_UNSPECIFIED;
        if (typeOfData == BY_FANTASYWORD) return str_BY_FANTASYWORD;
        if (typeOfData == BY_TRANSLATION) return str_BY_TRANSLATION;
        return null;
    }

    public void setTypeOfData(int typeOfData) {
        this.typeOfData = typeOfData;
    }
}
