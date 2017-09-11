package com.reebrandogmail.trackmycar.model;

/**
 * Created by renan.brando on 11/09/2017.
 */

public class History {

    private int id;
    private float latitude;
    private float longitude;
    private String timestamp;
    private String mapsURL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMapsURL() {
        return mapsURL;
    }

    public void setMapsURL(String mapsURL) {
        this.mapsURL = mapsURL;
    }
}
