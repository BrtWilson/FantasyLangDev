package com.example.shared.model.service.request;

public class RegisterRequest {

    private String firstName, lastName, userName, password;
    //private String imageURL;
    private String encodedImage;
    //private byte[] imageBytes;

    public RegisterRequest() {}

    public RegisterRequest(String firstName, String lastName, String userName, String password, String encodedImage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.encodedImage = encodedImage;
        //this.imageURL = imageURL;
        //this.imageBytes = null;
    }

    /*public RegisterRequest(String firstName, String lastName, String userName, String password, byte[] imageBytes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        //this.imageURL = null;
        //this.imageBytes = imageBytes;
    }*/

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
    //public String getImageURL() {return imageURL;}
    public String getEncodedImage() { return encodedImage; }
    //public byte[] getImageBytes() { return imageBytes; }

    public void setEncodedImage(String encodedImage) { this.encodedImage = encodedImage; }
    //public void setImageBytes(byte[] imageBytes) { this.imageBytes = imageBytes; }
}