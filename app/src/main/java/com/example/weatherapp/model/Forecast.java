package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Forecast {
    @SerializedName("forecastday")
    private List<Forecastday> forecastday;

    // Getters
    public List<Forecastday> getForecastday() {
        return forecastday;
    }

    // Setters (Opsional)
    public void setForecastday(List<Forecastday> forecastday) {
        this.forecastday = forecastday;
    }
}