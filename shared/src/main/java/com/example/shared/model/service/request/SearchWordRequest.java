package com.example.shared.model.service.request;

import com.example.shared.model.domain.Dictionary;

/**
 * Contains all the information needed to make a login request.
 */
public class SearchWordRequest extends ListRequest{

    public static final int BY_UNSPECIFIED = 0;
    public static final int BY_TRANSLATION = 1;
    public static final int BY_FANTASYWORD = 2;
    public static final String str_BY_UNSPECIFIED = "Unspecified";
    public static final String str_BY_TRANSLATION = "Translation";
    public static final String str_BY_FANTASYWORD = "FantasyWord";

    private String searchByData;
    private String languageid;
    private int typeOfData;

    /**
     *
     * @param searchByData the string entered by user to find corresponding words
     * @param languageid the id of the language being searched
     * @param typeOfData probably "Translation"
     */
    public SearchWordRequest(String searchByData, String languageid, int limit, Dictionary lastWord, int typeOfData) {
        super(languageid, limit, lastWord);
        this.searchByData = searchByData;
        this.languageid = languageid;
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

    public String getLanguageid() {
        return languageid;
    }

    public void setLanguageid(String languageid) {
        this.languageid = languageid;
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
