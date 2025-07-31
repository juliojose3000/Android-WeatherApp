package com.loaizasoftware.presentation.fragments.viewcontrollers;

import com.loaizasoftware.domain.models.WeatherResponse;
import com.loaizasoftware.presentation.databinding.FragmentWeatherBinding;

public class WeatherViewController {
    private final FragmentWeatherBinding binding;

    public WeatherViewController(FragmentWeatherBinding binding) {
        this.binding = binding;
    }

    public void buildUI(WeatherResponse response) {
        // Example: setting text or style
        binding.weatherMain.setText(response.weather.getFirst().main);
        binding.weatherDescription.setText(response.weather.getFirst().description);

    }
}