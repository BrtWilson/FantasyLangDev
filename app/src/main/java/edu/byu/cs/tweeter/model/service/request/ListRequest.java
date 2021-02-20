package edu.byu.cs.tweeter.model.service.request;

abstract class ListRequest implements IListRequest{
    private final String userAlias;
    private final int limit;
    private final String lastDataKey;

    /**
     * Creates an instance.
     *
     * @param userAlias the alias of the user whose followees are to be returned.
     * @param limit the maximum number of followees to return.
     * @param lastDataKey the alias of the last followee that was returned in the previous request (null if
     *                     there was no previous request or if no followees were returned in the
     *                     previous request).
     */
    public ListRequest(String userAlias, int limit, String lastDataKey) {
        this.userAlias = userAlias;
        this.limit = limit;
        this.lastDataKey = lastDataKey;
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
    public String getLastDataKey() {
        return lastDataKey;
    }
}
