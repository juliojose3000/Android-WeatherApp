package com.loaizasoftware.domain.usecases;

import java.util.concurrent.CompletableFuture;

import com.loaizasoftware.domain.models.WeatherData;
import com.loaizasoftware.domain.repository.WeatherRepository;

import javax.inject.Inject;

public class GetWeatherByCityUseCase extends UseCase<String, WeatherData> {

    private final WeatherRepository repository;

    @Inject
    public GetWeatherByCityUseCase(WeatherRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<WeatherData> run(String cityName) {
        return repository.getWeather(cityName);
    }
}
