package com.example.shared.model.service.response;

import java.util.List;
import java.util.Objects;

import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.FollowingRequest;

/**
 * A paged response for a {@link com.example.shared.model.service.request.FollowerRequest}.
 */
public class FollowerResponse extends PagedResponse {

    private List<User> followers;
    private final Integer numFollowers;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful. Sets the
     * success and more pages indicators to false.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public FollowerResponse(String message) {
        super(false, message, false);
        this.numFollowers = null;
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param followers the followers to be included in the result.
     * @param hasMorePages an indicator of whether more data is available for the request.
     */
    public FollowerResponse(List<User> followers, boolean hasMorePages) {
        super(true, hasMorePages);
        this.followers = followers;
        this.numFollowers = null;
    }

    /**
     * Constructor for sending responses corresponding to requests for the number of followees a user has
     * @param numFollowers the number of the user's followees
     */
    public FollowerResponse(int numFollowers) {
        super(true,false);
        this.followers = null;
        this.numFollowers = numFollowers;
    }

    /**
     * Returns the followers for the corresponding request.
     *
     * @return the followers.
     */
    public List<User> getFollowers() {
        return followers;
    }

    public Integer getNumFollowers() { return numFollowers; }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        FollowerResponse that = (FollowerResponse) param;

        return (Objects.equals(followers, that.followers) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(followers);
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }
}
