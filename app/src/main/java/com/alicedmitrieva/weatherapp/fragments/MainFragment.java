package com.alicedmitrieva.weatherapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alicedmitrieva.weatherapp.R;
import com.alicedmitrieva.weatherapp.adapters.WeatherDataPagerAdapter;
import com.alicedmitrieva.weatherapp.models.Day;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private static final String ARGUMENT_DAY_LIST = "day list";
    private static final String ARGUMENT_UNIT = "unit";

    static List<Day> dayList = new ArrayList<>();

    private ViewPager viewPager;

    public static MainFragment newInstance(List<Day> dayList, String currentUnit) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_DAY_LIST, new ArrayList<>(dayList));
        args.putString(ARGUMENT_UNIT, currentUnit);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        viewPager = view.findViewById(R.id.viewPager);

        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalArgumentException("arguments == null");
        }

        ArrayList list = arguments.getParcelableArrayList(ARGUMENT_DAY_LIST);

        String unit = arguments.getString(ARGUMENT_UNIT);

        WeatherDataPagerAdapter weatherDataPagerAdapter = new WeatherDataPagerAdapter(getChildFragmentManager(), list, unit);
        viewPager.setAdapter(weatherDataPagerAdapter);

        return view;
    }
}
