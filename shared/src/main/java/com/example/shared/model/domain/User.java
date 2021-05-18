package com.example.shared.model.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

/**
 * Represents a user in the system.
 */
public class User implements Comparable<User>, Serializable {

    private String userName;
    private String name;
    private String password;

    public User() {}

    public User(String userName, String name, String password) {
        this.userName = userName;
        this.name = name;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        User that = (User) param;

        return (Objects.equals(userName, that.userName) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.password, that.password));
    }

    @Override
    public String toString() {
        return "User{" +
                "User Name ='" + userName + '\'' +
                ", Name ='" + name + '\'';
    }

    @Override
    public int compareTo(User user) {
        return this.getUserName().compareTo(user.getUserName());
    }

}
