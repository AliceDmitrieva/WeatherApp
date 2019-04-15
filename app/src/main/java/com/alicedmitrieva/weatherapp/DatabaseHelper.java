package com.alicedmitrieva.weatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "weatherInfoManager";

    private interface CityTableColumns extends BaseColumns {

        String KEY_CITY_POSITION = "city_position";

        String TABLE_CITIES = "cities";

        String CREATE_TABLE_CITIES = "CREATE TABLE " + TABLE_CITIES
                + "(" + _ID + " INTEGER PRIMARY KEY,"
                + KEY_CITY_POSITION + " INTEGER" + ")";
    }

    private interface DayTableColumns extends BaseColumns {

        String KEY_DATE = "date";

        String TABLE_DAYS = "days";

        String CREATE_TABLE_DAYS = "CREATE TABLE " + TABLE_DAYS
                + "(" + _ID + " INTEGER PRIMARY KEY,"
                + KEY_DATE + " INTEGER" + ")";
    }

    private interface DetailsTableColumns extends BaseColumns {

        String KEY_DAY_ID = "day_id";
        String KEY_TIME = "time";
        String KEY_TEMPERATURE = "temperature";
        String KEY_DESCRIPTION = "description";

        String TABLE_DETAILS = "details";

        String CREATE_TABLE_DETAILS = "CREATE TABLE " + TABLE_DETAILS
                + "(" + _ID + " INTEGER PRIMARY KEY,"
                + KEY_TIME + " DATETIME,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_TEMPERATURE + " REAL,"
                + KEY_DAY_ID + " INTEGER,"
                + " FOREIGN KEY (" + KEY_DAY_ID + ") REFERENCES " + DayTableColumns.TABLE_DAYS + "(" + DayTableColumns._ID + "));";
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CityTableColumns.CREATE_TABLE_CITIES);
        db.execSQL(DayTableColumns.CREATE_TABLE_DAYS);
        db.execSQL(DetailsTableColumns.CREATE_TABLE_DETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CityTableColumns.TABLE_CITIES);
        db.execSQL("DROP TABLE IF EXISTS " + DayTableColumns.TABLE_DAYS);
        db.execSQL("DROP TABLE IF EXISTS " + DetailsTableColumns.TABLE_DETAILS);

        onCreate(db);
    }

    public void clearCityPosition() {
        String clearTableCities = "DELETE FROM " + CityTableColumns.TABLE_CITIES;
        getReadableDatabase().execSQL(clearTableCities);
    }

    public void clearWeatherData() {
        String clearTableWeekDays = "DELETE FROM " + DayTableColumns.TABLE_DAYS;
        String clearTableSections = "DELETE FROM " + DetailsTableColumns.TABLE_DETAILS;

        getReadableDatabase().execSQL(clearTableWeekDays);
        getReadableDatabase().execSQL(clearTableSections);
    }

    public void addCityPosition(int cityPosition) {
        clearCityPosition();
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cityValues = new ContentValues();
        cityValues.put(CityTableColumns.KEY_CITY_POSITION, cityPosition);
        database.insert(CityTableColumns.TABLE_CITIES, null, cityValues);
    }

    public void addWeatherData(List<Day> weatherData) {
        clearWeatherData();
        SQLiteDatabase database = this.getWritableDatabase();

        for (Day day : weatherData) {
            ContentValues dayValues = new ContentValues();
            dayValues.put(DayTableColumns.KEY_DATE, DataConverter.convertStringtoLong(day.getDate().toString()));
            long dayId = database.insert(DayTableColumns.TABLE_DAYS, null, dayValues);

            for (WeatherData details : day.getDetailInformation()) {
                ContentValues detailValues = new ContentValues();
                detailValues.put(DetailsTableColumns.KEY_TIME, details.getTime().toString());
                detailValues.put(DetailsTableColumns.KEY_DESCRIPTION, details.getDescription());
                detailValues.put(DetailsTableColumns.KEY_TEMPERATURE, details.getTemperature());
                detailValues.put(DetailsTableColumns.KEY_DAY_ID, dayId);

                database.insert(DetailsTableColumns.TABLE_DETAILS, null, detailValues);
            }
        }
    }

    public int getCityPosition() {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cityCursor = database.query(CityTableColumns.TABLE_CITIES, null, null, null,
                null, null, null);

        int cityValue = 0;
        for (cityCursor.moveToFirst(); !cityCursor.isAfterLast(); cityCursor.moveToNext()) {
            cityValue = cityCursor.getInt(cityCursor.getColumnIndex(CityTableColumns.KEY_CITY_POSITION));
        }
        return cityValue;
    }


    public List<Day> getWeatherData() {
        SQLiteDatabase database = this.getReadableDatabase();
        List<Day> weatherData = new ArrayList<>();

        Cursor dayCursor = database.query(DayTableColumns.TABLE_DAYS, null, null, null,
                null, null, null);

        for (dayCursor.moveToFirst(); !dayCursor.isAfterLast(); dayCursor.moveToNext()) {
            long dayId = dayCursor.getLong(dayCursor.getColumnIndex(DayTableColumns._ID));

            long value = dayCursor.getLong(dayCursor.getColumnIndex(DayTableColumns.KEY_DATE));
            Date date = new Date(value);

            List<WeatherData> list = new ArrayList<>();
            Cursor detailsCursor = database.query(DetailsTableColumns.TABLE_DETAILS,
                    null,
                    DetailsTableColumns.KEY_DAY_ID + " = " + dayId,
                    null,
                    null,
                    null,
                    null);

            for (detailsCursor.moveToFirst(); !detailsCursor.isAfterLast(); detailsCursor.moveToNext()) {
                Date time = new Date(detailsCursor.getString(detailsCursor.getColumnIndex(DetailsTableColumns.KEY_TIME)));
                String description = detailsCursor.getString(detailsCursor.getColumnIndex(DetailsTableColumns.KEY_DESCRIPTION));
                Double temperature = detailsCursor.getDouble(detailsCursor.getColumnIndex(DetailsTableColumns.KEY_TEMPERATURE));

                list.add(new WeatherData(time, description, temperature));
            }

            weatherData.add(new Day(date, list));
        }

        return weatherData;
    }
}
