package com.example.myapplication;

import android.net.Uri;

import java.util.Date;

public class User {
    private String Name;
    private String Surname;
    private String EMail;
    private String Password;
    private String PhoneNumber;
    private String imageUri;

    public User(String name, String surname, String EMail, String password, String phoneNumber, String imageUri) {
        Name = name;
        Surname = surname;
        this.EMail = EMail;
        Password = password;
        PhoneNumber = phoneNumber;
        this.imageUri = imageUri;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String email) {
        EMail = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getImageUri() { return imageUri; }

    public void setImageUri(String imageUri) { this.imageUri = imageUri; }
}
