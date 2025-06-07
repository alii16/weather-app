package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("name")
    private String name;
    @SerializedName("region")
    private String region;
    @SerializedName("country")
    private String country;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lon")
    private double lon;
    @SerializedName("localtime")
    private String localtime;

    // Getters
    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getLocaltime() {
        return localtime;
    }

    // Setters (Opsional)
    public void setName(String name) {
        this.name = name;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setLocaltime(String localtime) {
        this.localtime = localtime;
    }
}