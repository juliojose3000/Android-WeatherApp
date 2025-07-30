package com.loaizasoftware.data.repositories_impl;

import com.loaizasoftware.data.models.WeatherResponseDTO;
import com.loaizasoftware.data.network.WeatherApiService;
import com.loaizasoftware.domain.models.WeatherResponse;
import com.loaizasoftware.domain.repository.WeatherRepository;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class WeatherRepositoryImpl implements WeatherRepository {

    private final WeatherApiService apiService;
    private static final String API_KEY = "93dc26e962f6e56f70e239e538b36285";
    private static final String UNITS = "metric";

    @Inject
    public WeatherRepositoryImpl(WeatherApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public CompletableFuture<WeatherResponse> getWeather(String cityName) {

        CompletableFuture<WeatherResponse> future = new CompletableFuture<>();

        Call<WeatherResponseDTO> call = apiService.getCurrentWeather("Cartago", API_KEY, UNITS);

        call.enqueue(new Callback<>() {

            @Override
            public void onResponse(Call<WeatherResponseDTO> call, Response<WeatherResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    future.complete(response.body().toDomainModel());
                } else {
                    future.completeExceptionally(
                            new Exception("API Error: " + response.code() + " " + response.message())
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
