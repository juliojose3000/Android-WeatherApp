package com.loaizasoftware.domain.usecases;

import java.util.concurrent.CompletableFuture;

import com.loaizasoftware.domain.models.WeatherResponse;
import com.loaizasoftware.domain.repository.WeatherRepository;

import javax.inject.Inject;

public class GetWeatherUseCase extends UseCase<String, WeatherResponse> {

    private final WeatherRepository repository;

    @Inject
    public GetWeatherUseCase(WeatherRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<WeatherResponse> run(String cityName) {
        return repository.getWeather(cityName);
    }
}
