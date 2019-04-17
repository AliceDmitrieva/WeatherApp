package com.alicedmitrieva.weatherapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Day implements Parcelable {

    @NonNull
    private final Date date;
    @NonNull
    private final List<WeatherData> detailInformation;

    public Day(@NonNull Date date, @NonNull List<WeatherData> detailInformation) {
        this.date = date;
        this.detailInformation = Collections.unmodifiableList(detailInformation);
    }

    @NonNull
    public Date getDate() {
        return date;
    }

    @NonNull
    public List<WeatherData> getDetailInformation() {
        return detailInformation;
    }

    protected Day(Parcel in) {
        long tmpDate = in.readLong();
        date = tmpDate != -1 ? new Date(tmpDate) : null;
        if (in.readByte() == 0x01) {
            detailInformation = new ArrayList<WeatherData>();
            in.readList(detailInformation, WeatherData.class.getClassLoader());
        } else {
            detailInformation = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(date != null ? date.getTime() : -1L);
        if (detailInformation == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(detailInformation);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Day> CREATOR = new Parcelable.Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel in) {
            return new Day(in);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };
}