package com.alicedmitrieva.weatherapp;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class RespondWeatherDataTask extends AsyncTask<String, Void, List<Day>> {

    @NonNull
    private WeatherDataListener listener;
    @Nullable
    private Exception occurredError;

    public RespondWeatherDataTask(@NonNull WeatherDataListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Day> doInBackground(String... arg) {
        String forecastURL = "http://api.openweathermap.org/data/2.5/forecast?APPID=e79c6b6753eae1b7c303c76c35478554&units=metric&q=Moscow";
        String jsonString = NetworkUtils.readUrl(forecastURL);

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
