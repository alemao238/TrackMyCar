package com.reebrandogmail.trackmycar.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renanribeirobrando on 25/07/17.
 */

public class User implements Parcelable{


    private int id;
    @SerializedName("usuario")
    private String user;
    private String mail;
    @SerializedName("senha")
    private String password;
    private String phone;

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

    protected User(Parcel in) {
        id = in.readInt();
        user = in.readString();
        password = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(user);
        dest.writeString(password);
    }

}
