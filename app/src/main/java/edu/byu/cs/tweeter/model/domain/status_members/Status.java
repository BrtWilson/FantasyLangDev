package edu.byu.cs.tweeter.model.domain.status_members;

import edu.byu.cs.tweeter.model.domain.User;

public class Status {
    private String message;
    private UserMentions userMentions;
    private UrlMentions urlMentions;
    private String date;
    private User correspondingUser;

    public Status(String message, String time, User correspondingUser) {
        this.message = message;
        this.date = time;
        this.correspondingUser = correspondingUser;
        findMentions();
    }

    private void findMentions() {
        userMentions = new UserMentions(message);
        urlMentions = new UrlMentions(message);
    }

    public UserMentions getUserMentions() {
        return userMentions;
    }

    public UrlMentions getUrls() {
        return urlMentions;
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
    public String toString() {
        return "Status{" +
                "correspondingUser='" + correspondingUser.getAlias() + '\'' +
                ", date='" + date + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
