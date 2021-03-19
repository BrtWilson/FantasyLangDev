package com.example.shared.model.service.response;

/**
 * Represents follow/unfollow responses. Contains a null instance of Follow class for unfollow requests
 */
public class FollowStatusResponse extends Response {

    private boolean relationship;

    public FollowStatusResponse(String message) { super(false,message); }

    public FollowStatusResponse(boolean relationship) {
        super(true,null);
        this.relationship = relationship;
    }

    public boolean relationshipExists() { return relationship; }
}

