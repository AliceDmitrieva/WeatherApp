package com.alicedmitrieva.weatherapp.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alicedmitrieva.weatherapp.R;
import com.alicedmitrieva.weatherapp.models.Day;
import com.alicedmitrieva.weatherapp.utils.Formatter;

import java.util.List;

public class WeatherDataPagerAdapter extends PagerAdapter {

    private Activity activity;
    private List<Day> weatherData;
    private String unit;

    public WeatherDataPagerAdapter(Activity activity, List<Day> weatherData, String unit) {
        this.activity = activity;
        this.weatherData = weatherData;
        this.unit = unit;
    }

    @Override
    public int getCount() {
        return weatherData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return o == view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = activity.getLayoutInflater().inflate(R.layout.fragment_one_day, container, false);
        container.addView(view);

        TextView dateTextView = view.findViewById(R.id.date);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        dateTextView.setText(Formatter.formatDate(weatherData.get(position).getDate()));
        OneDayWeatherDataAdapter adapter = new OneDayWeatherDataAdapter(activity, weatherData.get(position).getDetailInformation(), unit);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.activity));

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
