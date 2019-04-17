package com.alicedmitrieva.weatherapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alicedmitrieva.weatherapp.R;
import com.alicedmitrieva.weatherapp.adapters.OneDayWeatherDataAdapter;
import com.alicedmitrieva.weatherapp.models.Day;
import com.alicedmitrieva.weatherapp.models.WeatherData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OneDayFragment extends Fragment {

    private static final String ARGUMENT_WEATHER_DATA_LIST = "weather list";
    private static final String ARGUMENT_UNIT = "unit";
    private static final String ARGUMENT_DATE = "date";

    public static OneDayFragment newInstance(Day day, String currentUnit) {
        OneDayFragment fragment = new OneDayFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_WEATHER_DATA_LIST, new ArrayList<>(day.getDetailInformation()));
        args.putString(ARGUMENT_UNIT, currentUnit);
        args.putSerializable(ARGUMENT_DATE, day.getDate());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_one_day, null);

        TextView dateTextView = view.findViewById(R.id.date);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalArgumentException("arguments == null");
        }

        ArrayList<WeatherData> weatherDataList = arguments.getParcelableArrayList(ARGUMENT_WEATHER_DATA_LIST);
        if (weatherDataList == null) {
            throw new IllegalArgumentException("product list == null");
        }

        String unit = arguments.getString(ARGUMENT_UNIT);

        Date date = (Date) arguments.getSerializable(ARGUMENT_DATE);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String day = dateFormat.format(date);

        dateTextView.setText(day);
        OneDayWeatherDataAdapter adapter = new OneDayWeatherDataAdapter(this.getContext(), weatherDataList, unit);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        return view;
    }
}



