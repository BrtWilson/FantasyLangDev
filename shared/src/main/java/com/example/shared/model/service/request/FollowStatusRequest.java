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

    private  User currentUser;
    private  User otherUser;
    private  int requestType;
    private  AuthToken authToken;

    public FollowStatusRequest() {}

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

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setOtherUser(User otherUser) {
        this.otherUser = otherUser;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}

