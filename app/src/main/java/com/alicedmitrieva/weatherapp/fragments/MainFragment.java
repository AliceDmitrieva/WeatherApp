package com.alicedmitrieva.weatherapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    private static final String ARGUMENT_DAY_LIST = "day_list";
    private static final String ARGUMENT_UNIT = "unit";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static MainFragment newInstance(List<Day> dayList, String currentUnit) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_DAY_LIST, new ArrayList<>(dayList));
        args.putString(ARGUMENT_UNIT, currentUnit);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        ViewPager viewPager = view.findViewById(R.id.viewPager);

        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalArgumentException("arguments == null");
        }

        ArrayList dayList = arguments.getParcelableArrayList(ARGUMENT_DAY_LIST);
        if (dayList == null) {
            throw new IllegalArgumentException("dayList == null");
        }

        String unit = arguments.getString(ARGUMENT_UNIT);
        if (unit == null) {
            throw new IllegalArgumentException("unit == null");
        }

        WeatherDataPagerAdapter weatherDataPagerAdapter = new WeatherDataPagerAdapter(getChildFragmentManager(), dayList, unit);
        viewPager.setAdapter(weatherDataPagerAdapter);

        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }
}
