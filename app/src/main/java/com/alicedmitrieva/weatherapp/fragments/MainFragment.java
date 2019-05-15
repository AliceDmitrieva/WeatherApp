package com.alicedmitrieva.weatherapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.fragment_main, null);
        view.removeAllViews();
        ViewPager viewPager = new ViewPager(getContext());
        viewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        viewPager.setId(R.id.viewPager);
        view.addView(viewPager);

        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalArgumentException("arguments == null");
        }

        ArrayList<Day> dayList = arguments.getParcelableArrayList(ARGUMENT_DAY_LIST);
        if (dayList == null) {
            throw new IllegalArgumentException("dayList == null");
        }

        String unit = arguments.getString(ARGUMENT_UNIT);
        if (unit == null) {
            throw new IllegalArgumentException("unit == null");
        }

        WeatherDataPagerAdapter weatherDataPagerAdapter = new WeatherDataPagerAdapter(getActivity(), dayList, unit);
        viewPager.setAdapter(weatherDataPagerAdapter);

        return view;
    }
}
