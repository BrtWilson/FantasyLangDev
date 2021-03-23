package com.example.shared.model.service.response;

public class FollowResponse extends Response{

    private boolean following;


    FollowResponse(boolean success) {
        super(success);
    }

    FollowResponse(boolean success, String message) {
        super(success, message);
    }

    public FollowResponse(boolean success, String message, boolean following) {
        super(success, message);
        this.following = following;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }
}
