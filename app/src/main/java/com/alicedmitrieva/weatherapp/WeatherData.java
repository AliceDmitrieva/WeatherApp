package com.alicedmitrieva.weatherapp;

import android.support.annotation.NonNull;

import java.util.Date;

public class WeatherData {

    @NonNull private final Date time;
    @NonNull private final String description;
    private final double temperature;

    public WeatherData (@NonNull Date time, @NonNull String description, @NonNull double temperature) {
        this.time = time;
        this.description = description;
        this.temperature = temperature;
    }

    @NonNull
    public Date getTime() {
        return time;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public double getTemperature() {
        return temperature;
    }

}
