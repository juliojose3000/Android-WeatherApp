package com.loaizasoftware.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.room.Embedded;

import com.loaizasoftware.data.local.converters.Converters;
import com.loaizasoftware.domain.models.Clouds;
import com.loaizasoftware.domain.models.Coord;
import com.loaizasoftware.domain.models.Main;
import com.loaizasoftware.domain.models.Sys;
import com.loaizasoftware.domain.models.Weather;
import com.loaizasoftware.domain.models.WeatherData;
import com.loaizasoftware.domain.models.Wind;

import java.util.List;

@Entity(tableName = "weather")
@TypeConverters({Converters.class}) // You'll define this class
public class WeatherEntity {

    @Embedded
    public Coord coord;

    @TypeConverters({Converters.class}) // Convert List<Weather>
    public List<Weather> weather;

    public String base;

    @Embedded
    public Main main;

    public int visibility;

    @Embedded
    public Wind wind;

    @Embedded
    public Clouds clouds;

    public long dt;

    @Embedded
    public Sys sys;

    public int timezone;

    @PrimaryKey
    public int id;

    public String name;

    public int cod;

    public WeatherData toDomainModel() {
        WeatherData data = new WeatherData();
        data.coord = this.coord;
        data.weather = this.weather;
        data.base = this.base;
        data.main = this.main;
        data.visibility = this.visibility;
        data.wind = this.wind;
        data.clouds = this.clouds;
        data.dt = this.dt;
        data.sys = this.sys;
        data.timezone = this.timezone;
        data.id = this.id;
        data.name = this.name;
        data.cod = this.cod;
        return data;
    }

}
