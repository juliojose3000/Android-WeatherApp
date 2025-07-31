package com.loaizasoftware.presentation.fragments.viewcontrollers;

import android.annotation.SuppressLint;

import com.loaizasoftware.domain.models.Weather;
import com.loaizasoftware.domain.models.WeatherResponse;
import com.loaizasoftware.presentation.databinding.FragmentWeatherBinding;

public class WeatherViewController {
    private final FragmentWeatherBinding binding;

    public WeatherViewController(FragmentWeatherBinding binding) {
        this.binding = binding;
    }

    @SuppressLint("SetTextI18n")
    public void buildUI(WeatherResponse response) {

        Weather weather = response.weather.get(0);

        int temp = (int) response.main.temp;
        int tempMax = (int) response.main.temp_max;
        int tempMin = (int) response.main.temp_min;
        int tempFeelsLike = (int) response.main.feels_like;

        binding.tvLocation.setText(response.name);
        binding.tvWeatherMain.setText(weather.main);
        binding.tvWeatherDescription.setText(weather.description);
        binding.tvTemp.setText(temp + "°");
        binding.tvTempMaxMin.setText("Max "+tempMax + "° / Min " + tempMin+ "°");
        binding.tvTempFeelsLike.setText("Feels like "+tempFeelsLike + "°");
        binding.tvHumidity.setText("Humidity "+(response.main.humidity));
        binding.tvWind.setText("Wind "+response.wind.speed);
        binding.tvPressure.setText("Pressure "+(response.main.pressure));

    }
}