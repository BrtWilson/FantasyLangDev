package com.example.shared.model.service.response;

import java.util.Objects;

public class BasicResponse extends Response {

    public BasicResponse(boolean success, String message) {
        super(success,message);
    }


    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        FollowStatusResponse that = (FollowStatusResponse) param;

        return (Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }
}
