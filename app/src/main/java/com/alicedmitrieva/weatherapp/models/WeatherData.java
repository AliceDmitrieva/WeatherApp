package com.alicedmitrieva.weatherapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

public class WeatherData implements Parcelable {

    @NonNull
    private final Date time;
    @NonNull
    private final String icon;
    @NonNull
    private final String description;
    private final double temperature;

    public WeatherData (@NonNull Date time, @NonNull String icon, @NonNull String description, double temperature) {
        this.time = time;
        this.icon = icon;
        this.description = description;
        this.temperature = temperature;
    }

    @NonNull
    public Date getTime() {
        return time;
    }

    @NonNull
    public String getIcon() {return icon;}

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public double getTemperature() {
        return temperature;
    }

    protected WeatherData(Parcel in) {
        long tmpTime = in.readLong();
        time = tmpTime != -1 ? new Date(tmpTime) : null;
        icon = in.readString();
        description = in.readString();
        temperature = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time != null ? time.getTime() : -1L);
        dest.writeString(icon);
        dest.writeString(description);
        dest.writeDouble(temperature);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<WeatherData> CREATOR = new Parcelable.Creator<WeatherData>() {
        @Override
        public WeatherData createFromParcel(Parcel in) {
            return new WeatherData(in);
        }

        @Override
        public WeatherData[] newArray(int size) {
            return new WeatherData[size];
        }
    };
}
