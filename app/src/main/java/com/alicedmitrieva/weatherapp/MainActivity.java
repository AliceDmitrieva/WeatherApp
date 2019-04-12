package com.alicedmitrieva.weatherapp;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RespondWeatherDataTask.WeatherDataListener {

    ViewPager viewPager;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.cities));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        new RespondWeatherDataTask(MainActivity.this).execute();
    }

    @Override
    public void onWeatherDataRequestSuccess(@NonNull List<Day> weatherData) {
        viewPager.setAdapter(new WeatherDataPagerAdapter(weatherData));
    }

    @Override
    public void onWeatherDataRequestError(@NonNull Exception error) {

    }
}
