package com.loaizasoftware.domain.datasource;

import com.loaizasoftware.domain.models.WeatherModel;

import java.util.concurrent.CompletableFuture;

public interface RemoteWeatherDataSource {
    CompletableFuture<WeatherModel> fetchWeather(double lat, double lon);
}
