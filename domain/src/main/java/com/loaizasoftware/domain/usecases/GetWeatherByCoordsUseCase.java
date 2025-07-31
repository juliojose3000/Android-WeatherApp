package com.loaizasoftware.domain.usecases;

import com.loaizasoftware.domain.models.WeatherResponse;
import com.loaizasoftware.domain.repository.WeatherRepository;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class GetWeatherByCoordsUseCase extends UseCase<GetWeatherByCoordsUseCase.Params, WeatherResponse> {

    private final WeatherRepository repository;

    @Inject
    public GetWeatherByCoordsUseCase(WeatherRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<WeatherResponse> run(Params coords) {
        return repository.getWeather(coords.lat, coords.lon);
    }

    public static class Params {

        double lat;
        double lon;

        public Params(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }

    }

}