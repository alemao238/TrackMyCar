package com.reebrandogmail.trackmycar.Util;

import java.util.Date;

/**
 * Created by renanribeirobrando on 15/07/17.
 */

public class GPSTracker {

    private float latitude;
    private float longitude;
    private double speed;
    private String timestamp;
    private String mapsURL;
    private boolean power;
    private boolean door;
    private boolean acc;

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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
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

    public void setMapsURL(String mapsLink) {
        this.mapsURL = mapsLink;
    }

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    public boolean isDoor() {
        return door;
    }

    public void setDoor(boolean door) {
        this.door = door;
    }

    public boolean isAcc() {
        return acc;
    }

    public void setAcc(boolean acc) {
        this.acc = acc;
    }
}
