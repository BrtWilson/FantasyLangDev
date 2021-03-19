package com.example.shared.model.service.request;

import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;

/**
 * A class for sending follow status requests, either to get or update whether the logged-in user follows the passed user
 */
public class FollowStatusRequest {

    public static final int GET = 0;
    public static final int FOLLOW = 1;
    public static final int UNFOLLOW = 2;

    private final User currentUser;
    private final User otherUser;
    private final int requestType;
    private final AuthToken authToken;

    /**
     * Constructor for FollowStatusRequest class
     * @param otherUser the user to be followed/unfollowed
     * @param requestType a boolean set to true if the user desires to follow the specified user, false if the user desires to unfollow
     */
    public FollowStatusRequest(User otherUser, User currentUser, int requestType, AuthToken authToken) {
        this.otherUser = otherUser;
        this.currentUser = currentUser;
        this.requestType = requestType;
        this.authToken = authToken;
    }

    public User getOtherUser() { return otherUser; }
    public User getCurrentUser() { return currentUser; }
    public int getRequestType() { return requestType; }
    public AuthToken getAuthToken() { return authToken; }
}

