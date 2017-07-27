package com.reebrandogmail.trackmycar.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renanribeirobrando on 25/07/17.
 */

public class User {

    private int id;
    @SerializedName("usuario")
    private String user;
    @SerializedName("senha")
    private String password;

    public User(int id, String username, String password){
        super();
        this.id = id;
        this.user = username;
        this.password = password;
    }

    public User(String username, String password){
        super();
        this.user = username;
        this.password = password;
    }

    public User() {
        super();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
