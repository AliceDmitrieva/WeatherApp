package com.alicedmitrieva.weatherapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.alicedmitrieva.weatherapp.models.Day;
import com.alicedmitrieva.weatherapp.fragments.OneDayFragment;

import java.util.List;

public class WeatherDataPagerAdapter extends FragmentPagerAdapter {

    private List<Day> weatherData;
    private String unit;

    public WeatherDataPagerAdapter(FragmentManager fragmentManager, List<Day> weatherData, String unit) {
        super(fragmentManager);
        this.weatherData = weatherData;
        this.unit = unit;
    }

    @Override
    public Fragment getItem(int position) {
        return OneDayFragment.newInstance(weatherData.get(position), unit);
    }

    @Override
    public int getCount() {
        return weatherData.size();
    }
}
