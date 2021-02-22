package edu.byu.cs.tweeter.model.domain;

import edu.byu.cs.tweeter.model.domain.status_members.UrlMentions;
import edu.byu.cs.tweeter.model.domain.status_members.UserMentions;


public class Status {
    private String message;
    private UserMentions mUserMentions;
    private UrlMentions mUrlMentions;

    public Status(String message) {
        this.message = message;
        findMentions();
    }

    private void findMentions() {
        mUserMentions = new UserMentions(message);
        mUrlMentions = new UrlMentions(message);
    }

    public UserMentions getUserMentions() {
        return mUserMentions;
    }

    public UrlMentions getUrls() {
        return mUrlMentions;
    }
}
