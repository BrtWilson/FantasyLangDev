package com.example.shared.model.domain;


import com.example.shared.model.service.response.StatusArrayResponse;

import java.util.Objects;

public class Status {
    private String message;
    private String date;
    private User correspondingUser;

    public Status(String message) {
        this.message = message;
    }

    public Status(String s, String s1, User user1) {
        message = s;
        date = s1;
        correspondingUser = user1;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public User getCorrespondingUser() {
        return correspondingUser;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        Status that = (Status) param;

        return (Objects.equals(message, that.message) &&
                Objects.equals(date, that.date) &&
                Objects.equals(correspondingUser, that.correspondingUser));
    }
}
