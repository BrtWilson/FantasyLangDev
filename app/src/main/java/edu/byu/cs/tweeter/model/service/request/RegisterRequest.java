package edu.byu.cs.tweeter.model.service.request;

public class RegisterRequest {

    public String firstName, lastName, userName, password, imageURL;

    public RegisterRequest(String firstName, String lastName, String userName, String password, String imageURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.imageURL = imageURL;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public String getImageURL() {
        return imageURL;
    }
}