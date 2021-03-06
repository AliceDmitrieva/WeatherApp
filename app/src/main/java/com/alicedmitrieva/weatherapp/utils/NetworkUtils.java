package com.alicedmitrieva.weatherapp.utils;

import android.content.Context;

import com.alicedmitrieva.weatherapp.R;
import com.alicedmitrieva.weatherapp.models.WeatherData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    public static String readUrl(String urlString) {
        BufferedReader reader = null;
        try {
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                assert url != null;
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            try {
                if (reader != null) {
                    while ((read = reader.read(chars)) != -1)
                        buffer.append(chars, 0, read);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return buffer.toString();
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static String getIconUrl(Context context, WeatherData weatherData) {
        return context.getResources().getString(R.string.icon_url) + weatherData.getIcon() + ".png";
    }

    public static String getForecastUrl(Context context, String city) {
        return NetworkUtils.readUrl(context.getString(R.string.forecast_url) + city);
    }
}
