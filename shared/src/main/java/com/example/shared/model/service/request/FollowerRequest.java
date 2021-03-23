package com.example.shared.model.service.request;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followers for a specified follower.
 */
public class FollowerRequest {

    private  String userAlias;
    private  Integer limit;
    private  String lastFollowerAlias;
    private  boolean isNumFollowerRequest;


    /**
     * Creates an instance.
     *
     * @param userAlias the alias of the user whose followees are to be returned.
     * @param limit the maximum number of followees to return.
     * @param lastFollowerAlias the alias of the last followee that was returned in the previous request (null if
     *                     there was no previous request or if no followees were returned in the
     *                     previous request).
     */
    public FollowerRequest(String userAlias, int limit, String lastFollowerAlias) {
        this.userAlias = userAlias;
        this.limit = limit;
        this.lastFollowerAlias = lastFollowerAlias;
        this.isNumFollowerRequest = false;
    }

    public FollowerRequest() {
    }

    /**
     * Returns the follower whose followees are to be returned by this request.
     *
     * @return the follower.
     */
    public String getUserAlias() {
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
    public String getLastFollowerAlias() {
        return lastFollowerAlias;
    }

    public boolean isNumFollowerRequest() { return isNumFollowerRequest; }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setLastFollowerAlias(String lastFollowerAlias) {
        this.lastFollowerAlias = lastFollowerAlias;
    }

    public void setNumFollowerRequest(boolean numFollowerRequest) {
        isNumFollowerRequest = numFollowerRequest;
    }
}
