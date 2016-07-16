package com.nashipaechepkurui.mavuno.app;

import com.firebase.client.*;

/**
 * Created by carina on 7/16/16.
 */
public class User {

    private String id;
    private String name;
    private String phone;
    private String email; // phonenumber + "@mavuno.app"
    private String password;


    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getEmail() {
        return email;
    }

    // Create an alias email for the user "0712345678@mavuno.app"
    public void setEmail(String email) {
        this.email = email.concat("@mavuno.app");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void saveUser() {
        Firebase myFirebaseRef = new Firebase(MyApplication.FIREBASE_URL);
        myFirebaseRef = myFirebaseRef.child("users").child(getId());
        myFirebaseRef.setValue(this);
    }
}
