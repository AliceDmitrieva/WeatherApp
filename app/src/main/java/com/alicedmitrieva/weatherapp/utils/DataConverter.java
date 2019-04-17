package com.alicedmitrieva.weatherapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataConverter {

    public static double celsiusToFahrenheit(double temperature) {
        return temperature * 1.8 + 32;
    }

    public static double fahrenheitToCelsius(double temperature) {
        return (temperature - 32) / 1.8;
    }

    public static long convertStringtoLong (String stringDate) {
        long milliseconds = 0;
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        try {
            Date d = format.parse(stringDate);
            milliseconds = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }
}
