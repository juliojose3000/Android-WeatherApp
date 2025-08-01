package com.loaizasoftware.data.datasources_impl;

import com.loaizasoftware.data.models.WeatherResponseDTO;
import com.loaizasoftware.data.network.WeatherApiService;
import com.loaizasoftware.domain.datasource.RemoteWeatherDataSource;
import com.loaizasoftware.domain.models.WeatherModel;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteWeatherDataSourceImpl implements RemoteWeatherDataSource {

    private final WeatherApiService apiService;
    private static final String API_KEY = "93dc26e962f6e56f70e239e538b36285";
    private static final String UNITS = "imperial";

    @Inject
    public RemoteWeatherDataSourceImpl(WeatherApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public CompletableFuture<WeatherModel> fetchWeather(double lat, double lon) {

        Call<WeatherResponseDTO> call = apiService.getCurrentWeatherByCoords(lat, lon, API_KEY, UNITS);
        CompletableFuture<WeatherModel> future = new CompletableFuture<>();

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<WeatherResponseDTO> call, Response<WeatherResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherModel domainData = response.body().toDomainModel();
                    future.complete(domainData);
                } else {
                    future.completeExceptionally(
                            new Exception("API error: " + response.code() + " " + response.message())
                    );
                }
            }

            @Override
            public void onFailure(Call<WeatherResponseDTO> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });

        return future;

    }

}


