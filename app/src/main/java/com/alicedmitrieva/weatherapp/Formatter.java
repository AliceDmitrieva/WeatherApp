package com.alicedmitrieva.weatherapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {

    public static String formatTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String time = dateFormat.format(date);
        return time;
    }
}
