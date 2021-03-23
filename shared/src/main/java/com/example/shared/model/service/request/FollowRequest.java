package com.example.shared.model.service.request;

import com.example.shared.model.domain.AuthToken;

public class FollowRequest {
    private String currentUser;
    private AuthToken authToken;
    private String userToFollow;
    private boolean follow;

    public FollowRequest() {
    }

    public FollowRequest(String currentUser, AuthToken authToken, String userToFollow, boolean follow) {
        this.currentUser = currentUser;
        this.authToken = authToken;
        this.userToFollow = userToFollow;
        this.follow = follow;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public String getUserToFollow() {
        return userToFollow;
    }

    public void setUserToFollow(String userToFollow) {
        this.userToFollow = userToFollow;
    }

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }
}
