package com.alicedmitrieva.weatherapp.models;

import android.support.annotation.NonNull;

public class ExtraData {

    @NonNull
    private final String unit;
    @NonNull
    private final int cityIndex;

    public ExtraData(String unit, int cityIndex) {
        this.unit = unit;
        this.cityIndex = cityIndex;
    }

    public String getUnit() {
        return unit;
    }

    public int getCityIndex() {
        return cityIndex;
    }
}
