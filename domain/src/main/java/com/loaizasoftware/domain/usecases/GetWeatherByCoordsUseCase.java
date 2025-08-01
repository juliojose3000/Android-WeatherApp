package com.loaizasoftware.domain.usecases;

import com.loaizasoftware.core.base.UseCase;
import com.loaizasoftware.domain.models.WeatherModel;
import com.loaizasoftware.domain.repository.WeatherRepository;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class GetWeatherByCoordsUseCase extends UseCase<GetWeatherByCoordsUseCase.Params, WeatherModel> {

    private final WeatherRepository repository;

    @Inject
    public GetWeatherByCoordsUseCase(WeatherRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<WeatherModel> run(Params coords) {
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