package com.loaizasoftware.data.repositories_impl;

import android.content.Context;

import com.loaizasoftware.core.utils.NetworkUtils;
import com.loaizasoftware.data.local.database.AppDatabase;
import com.loaizasoftware.data.local.entities.WeatherEntity;
import com.loaizasoftware.data.models.WeatherResponseDTO;
import com.loaizasoftware.data.network.WeatherApiService;
import com.loaizasoftware.domain.models.WeatherData;
import com.loaizasoftware.domain.repository.WeatherRepository;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class WeatherRepositoryImpl implements WeatherRepository {

    private final WeatherApiService apiService;
    private final AppDatabase appDatabase;
    private static final String API_KEY = "93dc26e962f6e56f70e239e538b36285";
    private static final String UNITS = "imperial";

    private Context context;

    @Inject
    public WeatherRepositoryImpl(
            WeatherApiService apiService,
            AppDatabase appDatabase,
            @ApplicationContext Context context
    ) {
        this.apiService = apiService;
        this.appDatabase = appDatabase;
        this.context = context; // Save it in a field if needed
    }

    @Override
    public CompletableFuture<WeatherData> getWeather(double lat, double lon) {
        CompletableFuture<WeatherData> future = new CompletableFuture<>();

        if (NetworkUtils.isInternetAvailable(context)) {
            // Has internet – call API
            fetchDataFromApi(lat, lon, future);
        } else {
            // No internet – load from Room
            fetchDataFromLocalDB(future);
        }

        return future;
    }

    private void fetchDataFromApi(double lat, double lon, CompletableFuture<WeatherData> future) {
        Call<WeatherResponseDTO> call = apiService.getCurrentWeatherByCoords(lat, lon, API_KEY, UNITS);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<WeatherResponseDTO> call, Response<WeatherResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherData domainData = response.body().toDomainModel();
                    future.complete(domainData);

                    // Save to DB in background
                    CompletableFuture.runAsync(() -> {
                        appDatabase.weatherDao().delete();
                        appDatabase.weatherDao().saveWeatherData(response.body().toEntity());
                    });
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
    }

    private void fetchDataFromLocalDB(CompletableFuture<WeatherData> future) {
        CompletableFuture.runAsync(() -> {
            try {
                WeatherEntity entity = appDatabase.weatherDao().getWeatherData();
                if (entity != null) {
                    future.complete(entity.toDomainModel());
                } else {
                    future.completeExceptionally(new Exception("No cached weather data available"));
                }
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
    }



}
