package com.example.shared.model.service.request;

import java.util.List;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followees for a specified follower.
 */
public class StatusArrayRequest extends ListRequest implements IListRequest{ //TODO

    private String lastStatusDate;
    private Boolean feedInstead;

    /**
     * Creates an instance.
     *
     * @param userAlias the alias of the user whose followees are to be returned.
     * @param limit the maximum number of followees to return.
     * @param lastStatusDate the alias of the last followee that was returned in the previous request (null if
     *                     there was no previous request or if no followees were returned in the
     *                     previous request).
     * This is used for any Story request, rather than a feed request.
     */
    public StatusArrayRequest(String userAlias, int limit, String lastStatusDate) {
        this(userAlias, limit, lastStatusDate, false);
    }

    /**
     * Creates an instance.
     *
     * @param userAlias the alias of the user whose followees are to be returned.
     * @param limit the maximum number of followees to return.
     * @param lastStatusDate the alias of the last followee that was returned in the previous request (null if
     *                     there was no previous request or if no followees were returned in the
     *                     previous request).
     * @param feedInstead whether this request iis for a user feed instead of the target user's story
     */
    public StatusArrayRequest(String userAlias, int limit, String lastStatusDate, Boolean feedInstead) {
        super(userAlias, limit, lastStatusDate);
        this.lastStatusDate = lastStatusDate;
        this.feedInstead = feedInstead;
    }

    public StatusArrayRequest(){}

    public Boolean getFeedInstead() {
        return feedInstead;
    }


    /**
     * Returns the follower whose followees are to be returned by this request.
     *
     * @return the follower.
     */

    /**
     * Returns the number representing the maximum number of followees to be returned by this request.
     *
     * @return the limit.
     */

    /**
     * Returns the last followee that was returned in the previous request or null if there was no
     * previous request or if no followees were returned in the previous request.
     *
     * @return the last followee.
     */
    public String getLastStatusDate() {
        return lastStatusDate;
    }

    public void setLastStatusDate(String lastStatusDate) {
        this.lastStatusDate = lastStatusDate;
    }

    public void setFeedInstead(Boolean feedInstead) {
        this.feedInstead = feedInstead;
    }
}
