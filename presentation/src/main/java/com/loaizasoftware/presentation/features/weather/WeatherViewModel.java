package com.loaizasoftware.presentation.features.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loaizasoftware.domain.models.WeatherData;
import com.loaizasoftware.domain.usecases.GetWeatherByCoordsUseCase;

import javax.inject.Inject;

public class WeatherViewModel extends ViewModel {

    private final GetWeatherByCoordsUseCase getWeatherByCoordsUseCase;

    // LiveData for weather data
    private final MutableLiveData<WeatherData> _weatherData = new MutableLiveData<>();
    public final LiveData<WeatherData> weatherData = _weatherData;

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
    public WeatherViewModel(GetWeatherByCoordsUseCase getWeatherByCoordsUseCase) {
        this.getWeatherByCoordsUseCase = getWeatherByCoordsUseCase;
    }

    public void getWeatherByCoords(double lat, double lon) {
        _isLoading.setValue(true);
        _errorMessage.setValue(null);
        _isSuccess.setValue(false);

        getWeatherByCoordsUseCase.run(new GetWeatherByCoordsUseCase.Params(lat, lon))
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
     * Clear all data
     */
    public void clearData() {
        _weatherData.setValue(null);
        _errorMessage.setValue(null);
        _isLoading.setValue(false);
        _isSuccess.setValue(false);
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
