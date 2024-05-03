package com.example.demoapp;

public class User {
    private String uid,name,phonenumber,profileImage,about;
    public User(){
    }

    public User(String profileImage) {
        this.profileImage = profileImage;
    }

    public User(String uid, String name, String phonenumber, String profileImage, String about) {
        this.uid = uid;
        this.name = name;
        this.phonenumber = phonenumber;
        this.profileImage = profileImage;
        this.about = about;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
