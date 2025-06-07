package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class Current {
    @SerializedName("temp_c")
    private double tempC;
    @SerializedName("temp_f")
    private double tempF;
    @SerializedName("is_day")
    private int isDay; // 1 for day, 0 for night
    @SerializedName("condition")
    private Condition condition;
    @SerializedName("wind_kph")
    private double windKph;
    @SerializedName("humidity")
    private int humidity;

    // NEW FIELDS FOR ADDITIONAL WEATHER DETAILS
    @SerializedName("feelslike_c")
    private double feelslikeC;
    @SerializedName("uv")
    private double uv;
    @SerializedName("pressure_mb")
    private double pressureMb;
    @SerializedName("vis_km")
    private double visKm;
    @SerializedName("precip_mm")
    private double precipMm;


    // Getters
    public double getTempC() {
        return tempC;
    }

    public double getTempF() {
        return tempF;
    }

    public int getIsDay() {
        return isDay;
    }

    public Condition getCondition() {
        return condition;
    }

    public double getWindKph() {
        return windKph;
    }

    public int getHumidity() {
        return humidity;
    }

    // NEW GETTERS
    public double getFeelslikeC() {
        return feelslikeC; // Changed from feelslike_c to feelslikeC to match new field name
    }

    public double getUv() {
        return uv;
    }

    public double getPressureMb() {
        return pressureMb; // Changed from pressure_mb to pressureMb
    }

    public double getVisKm() {
        return visKm; // Changed from vis_km to visKm
    }

    public double getPrecipMm() {
        return precipMm; // Changed from precip_mm to precipMm
    }

    // Setters (Opsional, tapi direkomendasikan jika Anda perlu mengatur nilai ini secara manual)
    public void setTempC(double tempC) {
        this.tempC = tempC;
    }

    public void setTempF(double tempF) {
        this.tempF = tempF;
    }

    public void setIsDay(int isDay) {
        this.isDay = isDay;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public void setWindKph(double windKph) {
        this.windKph = windKph;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    // NEW SETTERS
    public void setFeelslikeC(double feelslikeC) {
        this.feelslikeC = feelslikeC;
    }

    public void setUv(double uv) {
        this.uv = uv;
    }

    public void setPressureMb(double pressureMb) {
        this.pressureMb = pressureMb;
    }

    public void setVisKm(double visKm) {
        this.visKm = visKm;
    }

    public void setPrecipMm(double precipMm) {
        this.precipMm = precipMm;
    }
}