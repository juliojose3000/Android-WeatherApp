package com.loaizasoftware.trackforcechallenge.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.loaizasoftware.domain.models.WeatherResponse;
import com.loaizasoftware.domain.usecases.GetWeatherUseCase;
import com.loaizasoftware.trackforcechallenge.R;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Inject
    GetWeatherUseCase getWeatherUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Fetch weather data
        fetchWeather("London");

    }

    private void fetchWeather(String cityName) {
        getWeatherUseCase.run(cityName)
                .thenAccept(this::handleWeatherResponse)
                .exceptionally(this::handleError);
    }

    private void handleWeatherResponse(WeatherResponse weatherResponse) {
        runOnUiThread(() -> {
            Log.d(TAG, "City: " + weatherResponse.name);
            Log.d(TAG, "Temperature: " + weatherResponse.main.temp + "°C");
            Log.d(TAG, "Description: " + weatherResponse.weather.get(0).description);

            // Update UI elements here
        });
    }

    private Void handleError(Throwable throwable) {
        runOnUiThread(() -> {
            Log.e(TAG, "Error fetching weather", throwable);
            // Show error message to user
        });
        return null;
    }

}