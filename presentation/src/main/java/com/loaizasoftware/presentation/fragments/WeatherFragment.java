package com.loaizasoftware.presentation.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loaizasoftware.presentation.R;
import com.loaizasoftware.presentation.databinding.FragmentWeatherBinding;
import com.loaizasoftware.presentation.fragments.viewcontrollers.WeatherViewController;
import com.loaizasoftware.presentation.viewmodels.WeatherViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WeatherFragment extends Fragment {

    private FragmentWeatherBinding binding = null;
    private WeatherViewController viewController = null;

    @Inject
    public WeatherViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false);
        viewController = new WeatherViewController(binding);

        viewModel.getWeather("Cartago");

        viewModel.weatherData.observe(getViewLifecycleOwner(), weather -> {
            viewController.buildUI(weather);
        });

        //viewController.buildUI();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}