package com.loaizasoftware.data.models;

import com.google.gson.annotations.SerializedName;
import com.loaizasoftware.domain.models.Clouds;
import com.loaizasoftware.domain.models.Coord;
import com.loaizasoftware.domain.models.Main;
import com.loaizasoftware.domain.models.Sys;
import com.loaizasoftware.domain.models.Weather;
import com.loaizasoftware.domain.models.WeatherModel;
import com.loaizasoftware.domain.models.Wind;

import java.util.List;


/**
 * Data Transfer Object (DTO) for weather API response deserialization.
 *
 * This class represents the complete structure of a weather API response from OpenWeatherMap
 * or similar weather services. It handles the mapping between JSON response fields and Java
 * objects using Gson annotations for proper deserialization.
 *
 * The DTO follows the data layer responsibility in Clean Architecture, serving as an
 * intermediary between the external API response and internal domain models. It provides
 * conversion methods to transform API data into domain models and database entities.
 *
 * <p>Key features:</p>
 * <ul>
 *   <li>Complete mapping of weather API response fields</li>
 *   <li>Conversion to domain model for business logic layer</li>
 *   <li>Conversion to database entity for persistence</li>
 *   <li>Proper JSON field mapping with @SerializedName annotations</li>
 * </ul>
 *
 */
public class WeatherResponseDTO {
    @SerializedName("coord")
    private Coord coord;

    @SerializedName("weather")
    private List<Weather> weather;

    @SerializedName("base")
    private String base;

    @SerializedName("main")
    private Main main;

    @SerializedName("visibility")
    private int visibility;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("clouds")
    private Clouds clouds;

    @SerializedName("dt")
    private long dt;

    @SerializedName("sys")
    private Sys sys;

    @SerializedName("timezone")
    private int timezone;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("cod")
    private int cod;

    // Default constructor
    public WeatherResponseDTO() {}

    // Full constructor
    public WeatherResponseDTO(Coord coord, List<Weather> weather, String base, Main main,
                              int visibility, Wind wind, Clouds clouds, long dt, Sys sys,
                              int timezone, int id, String name, int cod) {
        this.coord = coord;
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.visibility = visibility;
        this.wind = wind;
        this.clouds = clouds;
        this.dt = dt;
        this.sys = sys;
        this.timezone = timezone;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    // Getters and Setters
    public Coord getCoord() { return coord; }
    public void setCoord(Coord coord) { this.coord = coord; }

    public List<Weather> getWeather() { return weather; }
    public void setWeather(List<Weather> weather) { this.weather = weather; }

    public String getBase() { return base; }
    public void setBase(String base) { this.base = base; }

    public Main getMain() { return main; }
    public void setMain(Main main) { this.main = main; }

    public int getVisibility() { return visibility; }
    public void setVisibility(int visibility) { this.visibility = visibility; }

    public Wind getWind() { return wind; }
    public void setWind(Wind wind) { this.wind = wind; }

    public Clouds getClouds() { return clouds; }
    public void setClouds(Clouds clouds) { this.clouds = clouds; }

    public long getDt() { return dt; }
    public void setDt(long dt) { this.dt = dt; }

    public Sys getSys() { return sys; }
    public void setSys(Sys sys) { this.sys = sys; }

    public int getTimezone() { return timezone; }
    public void setTimezone(int timezone) { this.timezone = timezone; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCod() { return cod; }
    public void setCod(int cod) { this.cod = cod; }

    // Utility method to convert to domain model
    public WeatherModel toDomainModel() {
        WeatherModel domainModel =
                new WeatherModel();

        domainModel.coord = this.coord;
        domainModel.weather = this.weather;
        domainModel.base = this.base;
        domainModel.main = this.main;
        domainModel.visibility = this.visibility;
        domainModel.wind = this.wind;
        domainModel.clouds = this.clouds;
        domainModel.dt = this.dt;
        domainModel.sys = this.sys;
        domainModel.timezone = this.timezone;
        domainModel.id = this.id;
        domainModel.name = this.name;
        domainModel.cod = this.cod;

        return domainModel;
    }

    // Converts DTO to WeatherEntity for Room
    public com.loaizasoftware.data.local.entities.WeatherEntity toEntity() {
        com.loaizasoftware.data.local.entities.WeatherEntity entity =
                new com.loaizasoftware.data.local.entities.WeatherEntity();

        entity.coord = this.coord;
        entity.weather = this.weather;
        entity.base = this.base;
        entity.main = this.main;
        entity.visibility = this.visibility;
        entity.wind = this.wind;
        entity.clouds = this.clouds;
        entity.dt = this.dt;
        entity.sys = this.sys;
        entity.timezone = this.timezone;
        entity.id = this.id;
        entity.name = this.name;
        entity.cod = this.cod;

        return entity;
    }


}