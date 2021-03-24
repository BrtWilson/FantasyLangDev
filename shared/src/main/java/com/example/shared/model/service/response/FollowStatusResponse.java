package com.example.shared.model.service.response;

import java.util.Objects;

/**
 * Represents follow/unfollow responses. Contains a null instance of Follow class for unfollow requests
 */
public class FollowStatusResponse extends Response {

    private boolean relationship;

    public FollowStatusResponse() {
        super(true);
    }

    public FollowStatusResponse(String message) { super(false,message); }

    public FollowStatusResponse(boolean relationship) {
        super(true,null);
        this.relationship = relationship;
    }

    public boolean relationshipExists() { return relationship; }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        FollowStatusResponse that = (FollowStatusResponse) param;

        return (Objects.equals(relationship, that.relationshipExists()) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    public void setRelationship(boolean relationship) {
        this.relationship = relationship;
    }
}

