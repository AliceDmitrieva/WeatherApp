package com.alicedmitrieva.weatherapp;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {

    public static String formatTime(Date date) {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(date);
    }

    public static String formatTemperature(Context context, double temperature, String currentUnit) {
        if (currentUnit.equals(context.getString(R.string.pref_unit_fahrenheit_value))) {
            return Math.round(DataConverter.celsiusToFahrenheit(temperature)) + " °F";
        } else {
            return Math.round(temperature) + " °C";
        }
    }
}
