package com.example.shared.model.domain;


import java.util.Objects;

public class Status {
    private String message;
    private String date;
    private String correspondingUserAlias;

    public Status(String message) {
        this.message = message;
    }

    public Status(String s, String s1, String user1) {
        message = s;
        date = s1;
        correspondingUserAlias = user1;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getCorrespondingUserAlias() {
        return correspondingUserAlias;
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
                Objects.equals(correspondingUserAlias, that.correspondingUserAlias));
    }
}
