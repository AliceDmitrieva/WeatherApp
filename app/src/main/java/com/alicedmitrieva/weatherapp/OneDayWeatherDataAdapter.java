package com.alicedmitrieva.weatherapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class OneDayWeatherDataAdapter extends RecyclerView.Adapter<OneDayWeatherDataAdapter.WeatherDataViewHolder> {

    private List<WeatherData> weatherDataDetails;

    public OneDayWeatherDataAdapter(List<WeatherData> weatherDataDetails) {
        this.weatherDataDetails = weatherDataDetails;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public WeatherDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new WeatherDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherDataViewHolder holder, int position) {
        holder.bindProduct(weatherDataDetails.get(position));
    }

    @Override
    public int getItemCount() {
        return weatherDataDetails.size();
    }

    public class WeatherDataViewHolder extends RecyclerView.ViewHolder {

        TextView timeTextView;
        TextView descriptionTextView;
        TextView temperatureTextView;
        WeatherData weatherData;

        public WeatherDataViewHolder(View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.time);
            descriptionTextView = itemView.findViewById(R.id.description);
            temperatureTextView = itemView.findViewById(R.id.temperature);
        }

        void bindProduct(WeatherData weatherData) {
            this.weatherData = weatherData;

            DateFormat dateFormat = new SimpleDateFormat("HH:");
            String time = dateFormat.format(weatherData.getTime());

            timeTextView.setText(time + "00");
            descriptionTextView.setText(weatherData.getDescription());
            temperatureTextView.setText(weatherData.getTemperature() + "°С");

        }
    }
}

