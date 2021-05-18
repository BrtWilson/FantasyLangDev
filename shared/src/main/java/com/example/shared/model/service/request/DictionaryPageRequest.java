package com.example.shared.model.service.request;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followees for a specified follower.
 */
public class DictionaryPageRequest extends ListRequest implements IListRequest{

    private String languageID;
    private String lastWord;

    /**
     * Creates an instance.
     *
     * @param languageID the alias of the user whose followees are to be returned.
     * @param limit the maximum number of followees to return.
     * @param lastWord the alias of the last followee that was returned in the previous request (null if
     *                     there was no previous request or if no followees were returned in the
     *                     previous request).
     * This is used for any Story request, rather than a feed request.
     */
    public DictionaryPageRequest(String languageID, int limit, String lastWord) {
        super(languageID, limit, lastWord);
        this.lastWord = lastWord;
    }

    public DictionaryPageRequest(){}

    /**
     * Returns the last followee that was returned in the previous request or null if there was no
     * previous request or if no followees were returned in the previous request.
     *
     * @return the last followee.
     */
    public String getLastWord() {
        return lastWord;
    }

    public void setLastWord(String lastWord) {
        this.lastWord = lastWord;
    }

}
