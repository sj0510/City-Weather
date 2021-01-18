package com.example.weather;

import com.google.gson.annotations.SerializedName;
public class Example extends ExampleWind {

    @SerializedName("main")
    Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;



    }
}
