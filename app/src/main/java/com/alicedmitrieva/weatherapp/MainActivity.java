package com.alicedmitrieva.weatherapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RespondWeatherDataTask.WeatherDataListener, SharedPreferences.OnSharedPreferenceChangeListener {

    ViewPager viewPager;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        setupSharedPreferences();
        startSendingRequest();
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.android_action_bar_spinner_menu, menu);

        spinner = (Spinner) menu.findItem(R.id.spinner).getActionView();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.spinner_item,
                getResources().getStringArray(R.array.cities));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                startSendingRequest();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setAdapter(adapter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onWeatherDataRequestSuccess(@NonNull List<Day> weatherData) {
        viewPager.setAdapter(new WeatherDataPagerAdapter(MainActivity.this, weatherData, getCurrentUnit()));
    }

    @Override
    public void onWeatherDataRequestError(@NonNull Exception error) {

    }

    void startSendingRequest() {
        if (spinner != null) {
            new RespondWeatherDataTask(MainActivity.this, spinner.getSelectedItem().toString()).execute();
        }
    }

    private String getCurrentUnit() {
        return PreferenceManager.getDefaultSharedPreferences(this).getString
                (getString(R.string.pref_unit_key), getString(R.string.pref_unit_celsius_value));
    }

    private void changeCurrentUnit() {
        viewPager.setAdapter(new WeatherDataPagerAdapter(MainActivity.this, WeatherDataPagerAdapter.getWeatherData(), getCurrentUnit()));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("unit")) {
            changeCurrentUnit();
        }
    }
}
