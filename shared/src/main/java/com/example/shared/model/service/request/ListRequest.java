package com.example.shared.model.service.request;

import com.example.shared.model.domain.Dictionary;

abstract class ListRequest{
    private  String languageID;
    private  int limit;
    private  Dictionary lastWord;

    /**
     * Creates an instance.
     *
     * @param languageID the alias of the user whose followees are to be returned.
     * @param limit the maximum number of followees to return.
     * @param lastWord the alias of the last followee that was returned in the previous request (null if
     *                     there was no previous request or if no followees were returned in the
     *                     previous request).
     */
    public ListRequest(String languageID, int limit, Dictionary lastWord) {
        this.languageID = languageID;
        this.limit = limit;
        this.lastWord = lastWord;
    }

    public ListRequest() {
    }

    /**
     * Returns the follower whose followees are to be returned by this request.
     *
     * @return the follower.
     */
    public String getLanguageid() {
        return languageID;
    }

    /**
     * Returns the number representing the maximum number of followees to be returned by this request.
     *
     * @return the limit.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Returns the last followee that was returned in the previous request or null if there was no
     * previous request or if no followees were returned in the previous request.
     *
     * @return the last followee.
     */
    public Dictionary getLastdictionary() {
        return lastWord;
    }

    public void setLanguageid(String languageid) {
        this.languageID = languageid;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastdictionary(Dictionary lastdictionary) {
        this.lastWord = lastdictionary;
    }
}
