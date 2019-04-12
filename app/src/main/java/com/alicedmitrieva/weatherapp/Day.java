package com.alicedmitrieva.weatherapp;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Day {

    @NonNull
    private final Date date;
    @NonNull
    private final List<WeatherData> detailInformation;

    public Day(@NonNull Date date, @NonNull List<WeatherData> detailInformation) {
        this.date = date;
        this.detailInformation = Collections.unmodifiableList(detailInformation);
    }

    @NonNull
    public Date getDate() {
        return date;
    }

    @NonNull
    public List<WeatherData> getDetailInformation() {
        return detailInformation;
    }
}
