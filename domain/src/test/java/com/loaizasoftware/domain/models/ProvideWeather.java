package com.loaizasoftware.domain.models;

import java.util.Collections;

public class ProvideWeather {

    public static WeatherModel getMock() {

        WeatherModel weatherModel = new WeatherModel();

        // Coord
        Coord coord = new Coord();
        coord.lon = -83.9167;
        coord.lat = 9.8667;
        weatherModel.coord = coord;

        // Weather list
        Weather weather = new Weather();
        weather.main = "Clouds";
        weather.description = "overcast clouds";
        weather.icon = "04d";
        weatherModel.weather = Collections.singletonList(weather);

        // Base
        weatherModel.base = "stations";

        // Main
        Main main = new Main();
        main.temp = 70.05;
        main.feels_like = 70.2;
        main.temp_min = 69.51;
        main.temp_max = 71.76;
        main.pressure = 1018;
        main.humidity = 73;
        main.sea_level = 1018;
        main.grnd_level = 816;
        weatherModel.main = main;

        // Visibility
        weatherModel.visibility = 10000;

        // Wind
        Wind wind = new Wind();
        wind.speed = 6.44;
        wind.deg = 60;
        weatherModel.wind = wind;

        // Clouds
        Clouds clouds = new Clouds();
        clouds.all = 89;
        weatherModel.clouds = clouds;

        // Timestamp
        weatherModel.dt = 1753972180;

        // Sys
        Sys sys = new Sys();
        sys.type = 2;
        sys.country = "CR";
        sys.sunrise = 1753961119;
        sys.sunset = 1754006316;
        weatherModel.sys = sys;

        // Other fields
        weatherModel.timezone = -21600;
        weatherModel.id = 3624370;
        weatherModel.name = "Cartago";
        weatherModel.cod = 200;

        return weatherModel;

    }

}
