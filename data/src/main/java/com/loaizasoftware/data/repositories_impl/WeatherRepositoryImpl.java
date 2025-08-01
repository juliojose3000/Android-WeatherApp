package com.loaizasoftware.data.repositories_impl;

import com.loaizasoftware.data.utils.NetworkHandlerImpl;
import com.loaizasoftware.domain.datasource.LocalWeatherDataSource;
import com.loaizasoftware.domain.datasource.RemoteWeatherDataSource;
import com.loaizasoftware.domain.models.WeatherModel;
import com.loaizasoftware.domain.repository.WeatherRepository;
import com.loaizasoftware.domain.util.NetworkHandler;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WeatherRepositoryImpl implements WeatherRepository {

    private final LocalWeatherDataSource localDataSource;
    private final RemoteWeatherDataSource remoteDataSource;
    private final NetworkHandler networkHandler;

    @Inject
    public WeatherRepositoryImpl(
            LocalWeatherDataSource localDataSource,
            RemoteWeatherDataSource remoteWeatherSource,
            NetworkHandlerImpl networkHandler
    ) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteWeatherSource;
        this.networkHandler = networkHandler;
    }

    @Override
    public CompletableFuture<WeatherModel> getWeather(double lat, double lon) {
        if (networkHandler.isNetworkAvailable()) {
            return remoteDataSource.fetchWeather(lat, lon)
                    .thenApply(weather -> {
                        localDataSource.cacheWeather(weather); // save it for offline
                        return weather;
                    });
        } else {
            return localDataSource.getCachedWeather();
        }
    }

}