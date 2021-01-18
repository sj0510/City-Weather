package com.example.weather;

import com.google.gson.annotations.SerializedName;

public class ExampleWind {

    @SerializedName("wind")
    Wind wind;

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
