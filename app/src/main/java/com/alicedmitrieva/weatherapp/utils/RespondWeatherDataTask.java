package com.alicedmitrieva.weatherapp.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alicedmitrieva.weatherapp.models.Day;

import java.util.List;

public class RespondWeatherDataTask extends AsyncTask<String, Void, List<Day>> {

    @NonNull
    private WeatherDataListener listener;
    @NonNull
    private String city;
    @Nullable
    private Exception occurredError;

    public RespondWeatherDataTask(@NonNull WeatherDataListener listener, @NonNull String city) {
        this.listener = listener;
        this.city = city;
    }

    @Override
    protected List<Day> doInBackground(String... arg) {
        String jsonString = NetworkUtils.getForecastUrl((Context) listener, city);

        try {
            return WeatherDataParser.parseJSON(jsonString);
        } catch (Exception e) {
            occurredError = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Day> result) {
        if (occurredError == null) {
            listener.onWeatherDataRequestSuccess(result);
        } else {
            listener.onWeatherDataRequestError(occurredError);
        }
    }

    public interface WeatherDataListener {
        void onWeatherDataRequestSuccess(@NonNull List<Day> weatherData);

        void onWeatherDataRequestError(@NonNull Exception error);
    }
}
