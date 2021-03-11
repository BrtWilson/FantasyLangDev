package com.example.shared.model.domain;


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
}
