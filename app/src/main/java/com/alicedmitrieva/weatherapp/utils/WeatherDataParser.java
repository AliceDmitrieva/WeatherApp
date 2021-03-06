package com.alicedmitrieva.weatherapp.utils;

import android.support.annotation.NonNull;

import com.alicedmitrieva.weatherapp.models.Day;
import com.alicedmitrieva.weatherapp.models.WeatherData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WeatherDataParser {

    private static final String TAG_COUNT = "cnt";
    private static final String TAG_LIST = "list";
    private static final String TAG_DATE = "dt";
    private static final String TAG_WEATHER = "weather";
    private static final String TAG_ICON = "icon";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_MAIN = "main";
    private static final String TAG_TEMPERATURE = "temp";

    @NonNull
    public static List<Day> parseJSON(@NonNull String jsonString) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);
        int countOfResponds = jsonObject.getInt(TAG_COUNT);

        Map<Date, List<WeatherData>> detailsMap = new HashMap<>();

        for (int i = 0; i < countOfResponds; i++) {
            JSONObject forecastInfo = jsonObject.getJSONArray(TAG_LIST).getJSONObject(i);
            Date day = new Date(TimeUnit.SECONDS.toMillis(forecastInfo.getLong(TAG_DATE)));
            WeatherData details = new WeatherData(day,
                    forecastInfo.getJSONArray(TAG_WEATHER).getJSONObject(0).getString(TAG_ICON),
                    forecastInfo.getJSONArray(TAG_WEATHER).getJSONObject(0).getString(TAG_DESCRIPTION),
                    forecastInfo.getJSONObject(TAG_MAIN).getDouble(TAG_TEMPERATURE));

            Date date = clearTime(day);
            List<WeatherData> detailsList = detailsMap.get(date);
            if (detailsList == null) {
                detailsList = new ArrayList<>();
                detailsMap.put(date, detailsList);
            }
            detailsList.add(details);
        }

        List<Day> dayList = new ArrayList<>(detailsMap.size());
        for (Map.Entry<Date, List<WeatherData>> entry : detailsMap.entrySet()) {
            dayList.add(new Day(entry.getKey(), entry.getValue()));
        }
        Collections.sort(dayList, new DayComparator());
        return dayList;
    }

    private static Date clearTime(@NonNull Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private static final class DayComparator implements Comparator<Day> {
        @Override
        public int compare(Day o1, Day o2) {
            return o1.getDate().compareTo(o2.getDate());
        }
    }
}
