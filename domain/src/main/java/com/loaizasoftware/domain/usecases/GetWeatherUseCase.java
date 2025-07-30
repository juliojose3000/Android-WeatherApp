package com.loaizasoftware.domain.usecases;

import java.util.concurrent.CompletableFuture;
import com.loaizasoftware.domain.repository.WeatherRepository;

import javax.inject.Inject;

public class GetWeatherUseCase extends UseCase<Integer, String> {

    private final WeatherRepository repository;

    @Inject
    public GetWeatherUseCase(WeatherRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<String> run(Integer params) {
        return null;
    }
}
