package com.alicedmitrieva.weatherapp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class WeatherDataPagerAdapter extends PagerAdapter {

    Context context;
    List<Day> weatherData;

    private static class ViewHolder {
        TextView dateTextView;
        RecyclerView weatherDataRecyclerView;
    }

    public WeatherDataPagerAdapter(Context context, List<Day> weatherData) {
        this.context = context;
        this.weatherData = weatherData;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        Day item = weatherData.get(position);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String day = dateFormat.format(item.getDate());

        WeatherDataPagerAdapter.ViewHolder holder;
        View convertView = LayoutInflater.from(collection.getContext()).inflate(R.layout.one_day_screen, collection, false);

        holder = new WeatherDataPagerAdapter.ViewHolder();

        holder.dateTextView = convertView.findViewById(R.id.date);
        holder.weatherDataRecyclerView = convertView.findViewById(R.id.recycler_view);

        holder.dateTextView.setText(day);
        holder.weatherDataRecyclerView.setAdapter(new OneDayWeatherDataAdapter(item.getDetailInformation()));

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
        holder.weatherDataRecyclerView.setLayoutManager(layoutManager);
        holder.weatherDataRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        collection.addView(convertView);

        return convertView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return weatherData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
