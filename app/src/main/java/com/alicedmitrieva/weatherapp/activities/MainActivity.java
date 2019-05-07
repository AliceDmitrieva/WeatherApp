package com.alicedmitrieva.weatherapp.activities;

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

import com.alicedmitrieva.weatherapp.R;
import com.alicedmitrieva.weatherapp.fragments.MainFragment;
import com.alicedmitrieva.weatherapp.models.Day;
import com.alicedmitrieva.weatherapp.utils.DatabaseHelper;
import com.alicedmitrieva.weatherapp.utils.Formatter;
import com.alicedmitrieva.weatherapp.utils.RespondWeatherDataTask;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RespondWeatherDataTask.WeatherDataListener {

    private static final String FRAGMENT_TAG = "fragment tag";
    private static final int REQUEST_CODE = 1;

    private static DatabaseHelper databaseHelper;

    private Fragment fragment;
    private Spinner spinner;
    private boolean isDayDataSaved = false;
    private String currentUnit;
    private int cityIndex;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        currentUnit = (databaseHelper.getExtraData().getUnit() == null) ?
                getString(R.string.pref_unit_celsius_value) : databaseHelper.getExtraData().getUnit();
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
            setSelection();
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!isDayDataSaved) {
                    city = spinner.getSelectedItem().toString();
                    cityIndex = position;
                    fragment = null; // reset fragment for adding new one
                    sendRequest();
                } else {
                    addOrReplaceFragment(databaseHelper.getExtraData().getUnit());//
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
                startActivityForResult(intent, REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            String result = data != null ? data.getStringExtra(getString(R.string.extra_unit)) : null;
            if ((!currentUnit.equals(result)) && (result != null)) {
                currentUnit = result;
                databaseHelper.addExtraData(currentUnit, cityIndex);
                fragment = null;
                addOrReplaceFragment(currentUnit);
            }
        }
    }

    @Override
    public void onWeatherDataRequestSuccess(@NonNull List<Day> weatherData) {
        if (fragment == null) {
            fragment = (MainFragment.newInstance(weatherData, currentUnit));
        }
        replaceFragment(fragment);
        databaseHelper.addExtraData(currentUnit, cityIndex);
        databaseHelper.addWeatherData(weatherData);
    }

    @Override
    public void onWeatherDataRequestError(@NonNull Exception error) {
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        this.fragment = fragment;
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, fragment, FRAGMENT_TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    private void addOrReplaceFragment(String unit) {
        if (fragment == null) {
            fragment = MainFragment.newInstance(databaseHelper.getWeatherData(), unit);
        }
        replaceFragment(fragment);
    }

    private void sendRequest() {
        if (spinner != null) {
            new RespondWeatherDataTask(MainActivity.this, city).execute();
        }
    }

    private void setSelection() {
        cityIndex = databaseHelper.getExtraData().getCityIndex();
        spinner.setSelection(cityIndex);
    }

    private boolean isCurrentDateExisted() {
        if (!databaseHelper.getWeatherData().isEmpty()) {
            String currentDate = Formatter.formatDate(new Date());
            String firstSavedDate = Formatter.formatDate(databaseHelper.getWeatherData().get(0).getDate());
            return firstSavedDate.equals(currentDate);
        } else {
            return false;
        }
    }
}
