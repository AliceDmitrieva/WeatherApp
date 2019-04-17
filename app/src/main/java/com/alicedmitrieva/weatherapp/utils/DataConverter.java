package com.alicedmitrieva.weatherapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataConverter {

    public static double celsiusToFahrenheit(double temperature) {
        return temperature * 1.8 + 32;
    }

    public static long dateStringToMilliseconds(String stringDate) {
        long milliseconds = 0;
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        try {
            Date date = format.parse(stringDate);
            milliseconds = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }
}
