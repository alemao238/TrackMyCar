package com.reebrandogmail.trackmycar.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renanribeirobrando on 25/07/17.
 */

public class User {

    @SerializedName("usuario")
    private String user;
    @SerializedName("senha")
    private String password;

    public User(String username, String password){
        this.user = username;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
