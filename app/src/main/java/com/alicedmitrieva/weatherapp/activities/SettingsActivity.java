package com.alicedmitrieva.weatherapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.alicedmitrieva.weatherapp.R;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private String unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setupSharedPreferences();
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private String getUnit() {
        return unit;
    }

    private void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_unit_key))) {
            setUnit(sharedPreferences.getString(getString(R.string.pref_unit_key), getString(R.string.pref_unit_celsius_value)));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                sendDataToMainActivity();
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        sendDataToMainActivity();
        finish();
    }

    private void sendDataToMainActivity() {
        Intent intent = new Intent();
        intent.putExtra(getString(R.string.pref_unit_key), getUnit());
        setResult(RESULT_OK, intent);
    }
}
