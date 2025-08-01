package com.loaizasoftware.data.datasources_impl;

import com.loaizasoftware.data.local.dao.WeatherDao;
import com.loaizasoftware.data.local.entities.WeatherEntity;
import com.loaizasoftware.domain.datasource.LocalWeatherDataSource;
import com.loaizasoftware.domain.models.WeatherModel;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class LocalWeatherDataSourceImpl implements LocalWeatherDataSource {
    private final WeatherDao weatherDao;

    @Inject
    public LocalWeatherDataSourceImpl(WeatherDao weatherDao) {
        this.weatherDao = weatherDao;
    }

    @Override
    public CompletableFuture<WeatherModel> getCachedWeather() {

        CompletableFuture<WeatherModel> future = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
            try {
                WeatherEntity entity = weatherDao.getWeatherData();
                if (entity != null) {
                    future.complete(entity.toDomainModel());
                } else {
                    future.completeExceptionally(new Exception("No cached weather data available"));
                }
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    @Override
    public void cacheWeather(WeatherModel weatherModel) {
        CompletableFuture.runAsync(() -> {
            try {
                weatherDao.delete(); //The app retails only the latest weather data fetched from API
                weatherDao.saveWeatherData(new WeatherEntity(weatherModel));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}
