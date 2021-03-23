package com.example.shared.model.service.request;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followees for a specified user.
 */
public class FollowingRequest {

    private final String userAlias;
    private final Integer limit;
    private final String lastFolloweeAlias;
    private final boolean isNumFollowingRequest;

    /**
     * Creates an instance.
     *
     * @param userAlias the alias of the user whose followees are to be returned.
     * @param limit the maximum number of followees to return.
     * @param lastFolloweeAlias the alias of the last followee that was returned in the previous request (null if
     *                     there was no previous request or if no followees were returned in the
     *                     previous request).
     */




    public FollowingRequest(String userAlias, int limit, String lastFolloweeAlias) {
        this.userAlias = userAlias;
        this.limit = limit;
        this.lastFolloweeAlias = lastFolloweeAlias;
        this.isNumFollowingRequest = false;
    }

    public FollowingRequest() {
        this.userAlias = null;
        this.limit = null;
        this.lastFolloweeAlias = null;
        this.isNumFollowingRequest = true;
    }

    /**
     * Returns the user whose followees are to be returned by this request.
     *
     * @return the user.
     */
    public String getFollowingAlias() {
        return userAlias;
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
    public String getLastFolloweeAlias() {
        return lastFolloweeAlias;
    }

    public boolean isNumFollowingRequest() { return isNumFollowingRequest; }
}
