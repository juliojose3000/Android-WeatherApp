package com.loaizasoftware.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.loaizasoftware.data.local.entities.WeatherEntity;

@Dao
public interface WeatherDao {

    @Insert
    void saveWeatherData(WeatherEntity weatherEntity);

    @Query("DELETE FROM weather")
    void delete();

    @Query("SELECT * FROM weather LIMIT 1")
    WeatherEntity getWeatherData();

}
