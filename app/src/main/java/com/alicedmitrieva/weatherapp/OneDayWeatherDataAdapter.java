package com.alicedmitrieva.weatherapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class OneDayWeatherDataAdapter extends BaseAdapter {

    List<WeatherData> weatherDataDetails;

    private static class ViewHolder {
        TextView timeTextView;
        TextView descriptionTextView;
        TextView temperatureTextView;
    }

    public OneDayWeatherDataAdapter(List<WeatherData> weatherDataDetails) {
        this.weatherDataDetails = weatherDataDetails;
    }

    @Override
    public int getCount() {
        return weatherDataDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherDataDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        WeatherData item = (WeatherData) getItem(position);

        DateFormat dateFormat = new SimpleDateFormat("HH:");
        String time = dateFormat.format(item.getTime());

        OneDayWeatherDataAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

            holder = new OneDayWeatherDataAdapter.ViewHolder();

            holder.timeTextView = convertView.findViewById(R.id.time);
            holder.descriptionTextView = convertView.findViewById(R.id.description);
            holder.temperatureTextView = convertView.findViewById(R.id.temperature);

            convertView.setTag(holder);

        } else {
            holder = (OneDayWeatherDataAdapter.ViewHolder) convertView.getTag();
        }

        holder.timeTextView.setText(time + "00");
        holder.descriptionTextView.setText(item.getDescription());
        holder.temperatureTextView.setText(item.getTemperature() + "°С");

        return convertView;
    }
}

