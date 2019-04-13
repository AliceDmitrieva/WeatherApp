package com.alicedmitrieva.weatherapp;

public class DataConverter {

    public static double celsiusToFahrenheit(double temperature) {
        return temperature * 1.8 + 32;
    }

    public static double fahrenheitToCelsius(double temperature) {
        return (temperature - 32) / 1.8;
    }
}
