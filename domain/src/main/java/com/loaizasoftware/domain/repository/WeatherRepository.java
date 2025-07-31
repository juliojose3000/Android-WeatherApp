package com.loaizasoftware.domain.repository;

import com.loaizasoftware.domain.models.*;
import java.util.concurrent.CompletableFuture;

public interface WeatherRepository {
    //CompletableFuture<WeatherData> getWeather(String cityName);
    CompletableFuture<WeatherData> getWeather(double lat, double lon);
}
