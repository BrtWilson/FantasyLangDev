package com.example.shared.model.service.request;

/**
 * Contains all the information needed to make a login request.
 */
public class LoginRequest {

    private  String userName;
    private  String password;

    /**
     * Creates an instance.
     *
     * @param userName the username of the user to be logged in.
     * @param password the password of the user to be logged in.
     */
    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LoginRequest() {
    }

    /**
     * Returns the username of the user to be logged in by this request.
     *
     * @return the username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the password of the user to be logged in by this request.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
