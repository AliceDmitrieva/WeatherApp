package com.alicedmitrieva.weatherapp;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class WeatherDataPagerAdapter extends PagerAdapter {

    List<Day> weatherData;

    private static class ViewHolder {
        TextView dateTextView;
        ListView weatherDataListView;
    }

    public WeatherDataPagerAdapter(List<Day> weatherData) {
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
        holder.weatherDataListView = convertView.findViewById(R.id.list);

        holder.dateTextView.setText(day);
        holder.weatherDataListView.setAdapter(new OneDayWeatherDataAdapter(item.getDetailInformation()));

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
