package com.example.shared.model.service.request;

import com.example.shared.model.domain.Dictionary;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followees for a specified follower.
 */
public class DictionaryPageRequest extends ListRequest {

    private String languageid;
    private Dictionary lastdictionary;

    /**
     * Creates an instance.
     *
     * @param languageid the alias of the user whose followees are to be returned.
     * @param limit the maximum number of followees to return.
     * @param lastdictionary the alias of the last followee that was returned in the previous request (null if
     *                     there was no previous request or if no followees were returned in the
     *                     previous request).
     * This is used for any Story request, rather than a feed request.
     */
    public DictionaryPageRequest(String languageid, int limit, Dictionary lastdictionary) {
        super(languageid, limit, lastdictionary);
        this.lastdictionary = lastdictionary;
    }

    public DictionaryPageRequest(){}

    /**
     * Returns the last followee that was returned in the previous request or null if there was no
     * previous request or if no followees were returned in the previous request.
     *
     * @return the last followee.
     */
    public Dictionary getLastdictionary() {
        return lastdictionary;
    }

    public void setLastdictionary(Dictionary lastdictionary) {
        this.lastdictionary = lastdictionary;
    }

    @Override
    public String getLanguageid() {
        return languageid;
    }

    @Override
    public void setLanguageid(String languageid) {
        this.languageid = languageid;
    }
}
