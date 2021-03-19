package com.example.shared.model.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

/**
 * Represents a user in the system.
 */
public class User implements Comparable<User>, Serializable {

    private final String firstName;
    private final String lastName;
    private final String alias;
    private final String imageUrl;
    private String password;
    private byte [] imageBytes;
    //Feed Array
//    private StatusArray feed;
    //Story Array
//    private StatusArray story;
    //private ArrayList<User> followers;
    //private ArrayList<User> following;

    public User(String firstName, String lastName, String imageURL) {
        this(firstName, lastName, String.format("@%s%s", firstName, lastName), imageURL);
    }

    public User(String firstName, String lastName, String alias, String imageURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = formatAlias(alias);
        this.imageUrl = imageURL;
        //this.followers = new ArrayList<>();
        //this.following = new ArrayList<>();
    }

    public String formatAlias(String alias) {
        String correctAlias;

        if (alias.charAt(0) != '@') {
            correctAlias = String.format("@%s", alias);
        } else {
            correctAlias = alias;
        }
        return correctAlias;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return String.format("%s %s", firstName, lastName);
    }

    public String getAlias() {
        return alias;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public byte [] getImageBytes() {
        return imageBytes;
    }

    //public int getFollowerCount() { return followers.size(); }

//    public int getFolloweeCount() { return following.size(); }

//    public ArrayList getFollowers() { return followers; }

//    public ArrayList getFollowing() { return following; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*public void addFollower(User newFollower) {
        //Idk if this is how to check if object is current object
        if(followers.contains(newFollower) || newFollower == this) {
            return;
        }
        followers.add(newFollower);
    }*/

    /*public void addFollowing(User newFollowee) {
        //Idk if this is how to check if object is current object
        if(following.contains(newFollowee) || newFollowee == this) {
            return;
        }
        following.add(newFollowee);
    }*/

//    public void removeFollower(User follower) { followers.remove(follower); }

//    public void removeFollowee(User followee) { following.remove(followee); }

//    public boolean checkFollowStatus(User potentialFollowee) { return following.contains(potentialFollowee); }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return alias.equals(user.alias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", alias='" + alias + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public int compareTo(User user) {
        return this.getAlias().compareTo(user.getAlias());
    }

//    public StatusArray getFeed() {
//        return feed;
//    }

//    public void updateFeed(Status newStatus) {
//        feed.addStatus(newStatus);
//    }

//    public StatusArray getStory() {
//        return story;
//    }

//    public void postStatus(Status newStatus) {
//        story.addStatus(newStatus);
//    }
}
