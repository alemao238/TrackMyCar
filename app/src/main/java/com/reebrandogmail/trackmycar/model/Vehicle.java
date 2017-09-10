package com.reebrandogmail.trackmycar.model;

/**
 * Created by renanribeirobrando on 08/09/17.
 */

public class Vehicle {

    private String brand;
    private String model;
    private String licensePlate;
    private String color;


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
