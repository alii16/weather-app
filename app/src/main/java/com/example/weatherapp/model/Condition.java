package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class Condition {
    @SerializedName("text")
    private String text;
    @SerializedName("icon")
    private String icon;
    @SerializedName("code")
    private int code;

    // Getters
    public String getText() {
        return text;
    }

    public String getIcon() {
        if (icon != null && icon.startsWith("//")) {
            return "https:" + icon;
        }
        return icon;
    }

    public int getCode() {
        return code;
    }

    // Setters (Opsional)
    public void setText(String text) {
        this.text = text;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
