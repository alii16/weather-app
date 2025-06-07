package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class Day {
    @SerializedName("maxtemp_c")
    private double maxtempC;
    @SerializedName("mintemp_c")
    private double mintempC;
    @SerializedName("condition")
    private Condition condition;

    // Getters
    public double getMaxtempC() {
        return maxtempC;
    }

    public double getMintempC() {
        return mintempC;
    }

    public Condition getCondition() {
        return condition;
    }

    // Setters (Opsional)
    public void setMaxtempC(double maxtempC) {
        this.maxtempC = maxtempC;
    }

    public void setMintempC(double mintempC) {
        this.mintempC = mintempC;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}