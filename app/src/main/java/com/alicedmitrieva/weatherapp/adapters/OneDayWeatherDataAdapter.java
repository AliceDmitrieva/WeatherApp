package com.alicedmitrieva.weatherapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alicedmitrieva.weatherapp.utils.Formatter;
import com.alicedmitrieva.weatherapp.utils.NetworkUtils;
import com.alicedmitrieva.weatherapp.R;
import com.alicedmitrieva.weatherapp.models.WeatherData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OneDayWeatherDataAdapter extends RecyclerView.Adapter<OneDayWeatherDataAdapter.WeatherDataViewHolder> {

    private Context context;
    private List<WeatherData> weatherDataDetails;
    private String currentUnit;

    public OneDayWeatherDataAdapter(Context context, List<WeatherData> weatherDataDetails, String currentUnit) {
        this.context = context;
        this.weatherDataDetails = weatherDataDetails;
        this.currentUnit = currentUnit;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public WeatherDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new WeatherDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDataViewHolder holder, int position) {
        holder.bindWeatherData(weatherDataDetails.get(position));
    }

    @Override
    public int getItemCount() {
        return weatherDataDetails.size();
    }

    public class WeatherDataViewHolder extends RecyclerView.ViewHolder {

        TextView timeTextView;
        ImageView iconImageView;
        TextView descriptionTextView;
        TextView temperatureTextView;
        WeatherData weatherData;

        public WeatherDataViewHolder(View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.time);
            iconImageView = itemView.findViewById(R.id.icon);
            descriptionTextView = itemView.findViewById(R.id.description);
            temperatureTextView = itemView.findViewById(R.id.temperature);
        }

        void bindWeatherData(WeatherData weatherData) {
            this.weatherData = weatherData;

            timeTextView.setText(Formatter.formatTime(weatherData.getTime()));
            Picasso.get().load(NetworkUtils.getIconUrl(context, weatherData)).into(iconImageView);
            descriptionTextView.setText(weatherData.getDescription());
            temperatureTextView.setText(Formatter.formatTemperature(context, weatherData.getTemperature(), currentUnit));
        }
    }
}
