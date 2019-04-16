package com.alicedmitrieva.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RespondWeatherDataTask.WeatherDataListener {

    private static final String FRAGMENT_TAG = "fragment tag";
    private static DatabaseHelper databaseHelper;
    private Fragment fragment;
    private boolean isDayDataSaved = false;
    private String currentUnit;
    private int cityIndex;
    private String city;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        currentUnit = (databaseHelper.getExtraData().getUnit() == null) ? "celsius" : databaseHelper.getExtraData().getUnit();
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        this.fragment = fragment;
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, fragment, FRAGMENT_TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.android_action_bar_spinner_menu, menu);

        spinner = (Spinner) menu.findItem(R.id.spinner).getActionView();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.spinner_item,
                getResources().getStringArray(R.array.cities));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        isDayDataSaved = isCurrentDateExisted();
        if (isDayDataSaved) {
            cityIndex = databaseHelper.getExtraData().getCityIndex();
            spinner.setSelection(cityIndex);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!isDayDataSaved) {
                    city = spinner.getSelectedItem().toString();
                    cityIndex = position;
                    fragment = null;
                    sendRequest();
                } else {
                    restoreData(databaseHelper.getExtraData().getUnit());
                    isDayDataSaved = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(intent, 1);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onWeatherDataRequestSuccess(@NonNull List<Day> weatherData) {
        if (fragment == null) {
            fragment = (MainFragment.newInstance(weatherData, currentUnit));
        }
        changeFragment(fragment);
        databaseHelper.addExtraData(currentUnit, cityIndex);
        databaseHelper.addWeatherData(weatherData);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            String result = data != null ? data.getStringExtra("unit") : null;
            if ((!currentUnit.equals(result)) && (result != null)) {
                currentUnit = result;
                fragment = null;
                restoreData(currentUnit);
            }
        }
    }

    @Override
    public void onWeatherDataRequestError(@NonNull Exception error) {
        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
    }

    private void sendRequest() {
        if (spinner != null) {
            new RespondWeatherDataTask(MainActivity.this, city).execute();
        }
    }

    private boolean isCurrentDateExisted() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = (dateFormat.format(new Date()));
        if (!databaseHelper.getWeatherData().isEmpty()) {
            String firstSavedDate = dateFormat.format(databaseHelper.getWeatherData().get(0).getDate());
            return firstSavedDate.equals(currentDate);
        } else {
            return false;
        }
    }

    private void restoreData(String unit) {
        if (fragment == null) {
            fragment = MainFragment.newInstance(databaseHelper.getWeatherData(), unit);
        }
        changeFragment(fragment);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager()
                .putFragment(outState, FRAGMENT_TAG, fragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }
}
