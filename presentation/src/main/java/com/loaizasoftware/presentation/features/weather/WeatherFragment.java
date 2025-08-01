package com.loaizasoftware.presentation.features.weather;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loaizasoftware.core.ui.LoaderView;
import com.loaizasoftware.presentation.R;
import com.loaizasoftware.presentation.databinding.FragmentWeatherBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WeatherFragment extends Fragment {

    private FragmentWeatherBinding binding = null;
    private WeatherBackgroundManager backgroundManager;

    private double latitude;
    private double longitude;

    @Inject
    public WeatherViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            this.latitude = getArguments().getDouble("lat");
            this.longitude = getArguments().getDouble("lon");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        backgroundManager = new WeatherBackgroundManager(getContext(), binding.backgroundContainer);
        viewModel.getWeatherByCoords(latitude, longitude);
        initObservers();
        return binding.getRoot();
    }

    private void initObservers() {

        viewModel.weatherData.observe(getViewLifecycleOwner(), weather -> {
            if (weather != null && weather.weather != null && !weather.weather.isEmpty()) {
                String condition = weather.weather.get(0).main;
                backgroundManager.setWeatherBackground(condition);
            }
        });

        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            if(isLoading) {
                LoaderView.getInstance().showLoader();
            } else {
                LoaderView.getInstance().dismissLoader();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (backgroundManager != null) {
            backgroundManager.stopAllAnimations();
        }
        binding = null;
        viewModel.clearData();
    }

}