package edu.byu.cs.tweeter.model.domain;

import edu.byu.cs.tweeter.model.domain.status_members.UrlMentions;
import edu.byu.cs.tweeter.model.domain.status_members.UserMentions;


public class Status {
    private String message;
    private UserMentions mUserMentions;
    private UrlMentions mUrlMentions;
    private String date;
    private User correspondingUser;

    public Status(String message) {
        this.message = message;
        findMentions();
    }

    public Status(String s, String s1, User user1) {
        message = s;
        date = s1;
        correspondingUser = user1;
    }

    private void findMentions() {
        mUserMentions = new UserMentions(message);
        mUrlMentions = new UrlMentions(message);
    }

    public String getMessage() {
        return message;
    }

    public UserMentions getUserMentions() {
        return mUserMentions;
    }

    public UrlMentions getUrls() {
        return mUrlMentions;
    }

    public String getDate() {
        return date;
    }

    public User getCorrespondingUser() {
        return correspondingUser;
    }
}
