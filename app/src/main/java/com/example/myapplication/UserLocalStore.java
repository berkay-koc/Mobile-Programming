package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("mail", user.getEMail());
        spEditor.putString("password", user.getPassword());
        spEditor.putString("name", user.getName());
        spEditor.putString("phoneNr", user.getPhoneNumber());
        spEditor.putString("surname", user.getSurname());
        spEditor.putString("uri", user.getImageUri());
        spEditor.apply();
    }

    public User getLoggedInUser(){
        String name = userLocalDatabase.getString("name", "");
        String surname = userLocalDatabase.getString("surname", "");
        String password = userLocalDatabase.getString("password", "");
        String phoneNr = userLocalDatabase.getString("phoneNr", "");
        String mail = userLocalDatabase.getString("mail", "");
        String uri = userLocalDatabase.getString("uri", "");

        System.out.println(name + surname + password + phoneNr + mail + uri);

        User storedUser = new User(name, surname, mail, password, phoneNr, uri);

        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.apply();
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.apply();
    }
}