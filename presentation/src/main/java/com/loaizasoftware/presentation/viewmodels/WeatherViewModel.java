package com.loaizasoftware.presentation.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loaizasoftware.domain.models.WeatherResponse;
import com.loaizasoftware.domain.usecases.GetWeatherUseCase;

import javax.inject.Inject;

public class WeatherViewModel extends ViewModel {

    private final GetWeatherUseCase getWeatherUseCase;

    // LiveData for weather data
    private final MutableLiveData<WeatherResponse> _weatherData = new MutableLiveData<>();
    public final LiveData<WeatherResponse> weatherData = _weatherData;

    // LiveData for loading state
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoading = _isLoading;

    // LiveData for error messages
    private final MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    public final LiveData<String> errorMessage = _errorMessage;

    // LiveData for success state (optional)
    private final MutableLiveData<Boolean> _isSuccess = new MutableLiveData<>(false);
    public final LiveData<Boolean> isSuccess = _isSuccess;


    @Inject
    public WeatherViewModel(GetWeatherUseCase getWeatherUseCase) {
        this.getWeatherUseCase = getWeatherUseCase;
    }

    public void getWeather(String cityName) {
        if (cityName == null || cityName.trim().isEmpty()) {
            _errorMessage.setValue("City name cannot be empty");
            return;
        }

        _isLoading.setValue(true);
        _errorMessage.setValue(null);
        _isSuccess.setValue(false);

        getWeatherUseCase.run(cityName)
                .thenAccept(weather -> {
                    _isLoading.postValue(false);
                    _weatherData.postValue(weather);
                    _isSuccess.postValue(true);
                })
                .exceptionally(throwable -> {
                    _isLoading.postValue(false);
                    _isSuccess.postValue(false);
                    _errorMessage.postValue(getErrorMessage(throwable));
                    return null;
                });
    }

    /**
     * Refresh current weather data
     */
    public void refreshWeather(String cityName) {
        getWeather(cityName);
    }

    /**
     * Clear error message
     */
    public void clearError() {
        _errorMessage.setValue(null);
    }

    /**
     * Clear all data
     */
    public void clearData() {
        _weatherData.setValue(null);
        _errorMessage.setValue(null);
        _isLoading.setValue(false);
        _isSuccess.setValue(false);
    }

    /**
     * Get formatted temperature string
     */
    public String getFormattedTemperature() {
        WeatherResponse weather = _weatherData.getValue();
        if (weather != null && weather.main != null) {
            return Math.round(weather.main.temp) + "°C";
        }
        return "--°C";
    }

    /**
     * Get weather description
     */
    public String getWeatherDescription() {
        WeatherResponse weather = _weatherData.getValue();
        if (weather != null && weather.weather != null && !weather.weather.isEmpty()) {
            return weather.weather.get(0).description;
        }
        return "No description available";
    }

    /**
     * Get city name
     */
    public String getCityName() {
        WeatherResponse weather = _weatherData.getValue();
        if (weather != null) {
            return weather.name;
        }
        return "Unknown";
    }

    /**
     * Check if weather data is available
     */
    public boolean hasWeatherData() {
        return _weatherData.getValue() != null;
    }

    /**
     * Convert throwable to user-friendly error message
     */
    private String getErrorMessage(Throwable throwable) {
        if (throwable == null) {
            return "Unknown error occurred";
        }

        String message = throwable.getMessage();
        if (message == null || message.isEmpty()) {
            return "Network error occurred";
        }

        // Handle specific error cases
        if (message.contains("404")) {
            return "City not found";
        } else if (message.contains("401")) {
            return "Invalid API key";
        } else if (message.contains("timeout") || message.contains("Unable to resolve host")) {
            return "Network connection error";
        } else if (message.contains("API Error")) {
            return "Weather service temporarily unavailable";
        }

        return "Failed to get weather data: " + message;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Clean up any resources if needed
    }

}
