package com.alicedmitrieva.weatherapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class WeatherDataPagerAdapter extends FragmentPagerAdapter {

    private static List<Day> weatherData;
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
