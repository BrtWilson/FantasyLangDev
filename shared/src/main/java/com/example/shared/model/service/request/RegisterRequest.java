package com.example.shared.model.service.request;

public class RegisterRequest {

    private String name, userName, password;
    //private String imageURL;
    private String encodedImage;
    //private byte[] imageBytes;

    public RegisterRequest() {}

    public RegisterRequest(String name, String lastName, String userName, String password, String encodedImage) {
        this.name = name;
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

    public String getName() {
        return name;
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


    public void setName(String name) {
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEncodedImage(String encodedImage) { this.encodedImage = encodedImage; }
    //public void setImageBytes(byte[] imageBytes) { this.imageBytes = imageBytes; }
}