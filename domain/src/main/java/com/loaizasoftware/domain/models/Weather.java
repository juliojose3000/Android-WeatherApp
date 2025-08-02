package com.loaizasoftware.domain.models;

public class Weather {
    //public int id;
    public String main;
    public String description;
    public String icon;

    public WeatherType getWeatherType() {
        return WeatherType.fromCondition(main);
    }

    public String getDisplayCondition() {
        return getWeatherType().toDisplayString();
    }
}

