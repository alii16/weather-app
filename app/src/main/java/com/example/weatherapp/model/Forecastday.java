package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class Forecastday {
    @SerializedName("date")
    private String date;
    @SerializedName("day")
    private Day day;

    // Getters
    public String getDate() {
        return date;
    }

    public Day getDay() {
        return day;
    }

    // Setters (Opsional)
    public void setDate(String date) {
        this.date = date;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}