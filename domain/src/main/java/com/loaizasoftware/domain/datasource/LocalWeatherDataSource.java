package com.loaizasoftware.domain.datasource;

import com.loaizasoftware.domain.models.WeatherModel;

import java.util.concurrent.CompletableFuture;

public interface LocalWeatherDataSource {
    CompletableFuture<WeatherModel> getCachedWeather();
    void cacheWeather(WeatherModel model);
}
