package com.example.shared.model.service.response;

import java.util.List;
import java.util.Objects;

import com.example.shared.model.domain.User;

/**
 * A paged response for a {@link com.example.shared.model.service.request.FollowingRequest}.
 */
public class FollowingResponse extends PagedResponse {

    private List<User> followees;
    private final Integer numFollowing;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful. Sets the
     * success and more pages indicators to false.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public FollowingResponse(String message) {
        super(false, message, false);
        this.numFollowing = null;
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param followees the followees to be included in the result.
     * @param hasMorePages an indicator of whether more data is available for the request.
     */
    public FollowingResponse(List<User> followees, boolean hasMorePages) {
        super(true, hasMorePages);
        this.followees = followees;
        this.numFollowing = null;
    }

    public FollowingResponse(int numFollowing) {
        super(true,false);
        this.followees = null;
        this.numFollowing = numFollowing;
    }

    /**
     * Returns the followees for the corresponding request.
     *
     * @return the followees.
     */
    public List<User> getFollowees() {
        return followees;
    }

    public Integer getNumFollowing() { return numFollowing; }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        FollowingResponse that = (FollowingResponse) param;

        return (Objects.equals(followees, that.followees) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(followees);
    }
}
