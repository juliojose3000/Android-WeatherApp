package com.loaizasoftware.domain.repository;

import com.loaizasoftware.domain.models.*;
import java.util.concurrent.CompletableFuture;

public interface WeatherRepository {
    CompletableFuture<WeatherResponse> getWeather();
}
