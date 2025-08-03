package com.loaizasoftware.presentation.features.weather;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loaizasoftware.core.base.BaseFragment;
import com.loaizasoftware.presentation.R;
import com.loaizasoftware.presentation.databinding.FragmentWeatherBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WeatherFragment extends BaseFragment {

    private FragmentWeatherBinding binding = null;
    private WeatherBackgroundManager backgroundManager;

    private double latitude;
    private double longitude;

    public WeatherViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Scope ViewModel to Activity so it survives rotation
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);

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

        if(viewModel.weatherData.getValue() == null) {
            viewModel.getWeatherByCoords(latitude, longitude);
        }
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
                showLoader();
            } else {
                dismissLoader();
            }
        });

        viewModel.errorMessage.observe(getViewLifecycleOwner(), error -> {

            if(error == null) return;

            new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.error_message_title)
                    .setMessage(getString(R.string.error_message_description)+" "+error)
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, which) -> {
                        dialog.dismiss();
                        requireActivity().finish();
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (backgroundManager != null) {
            backgroundManager.stopAllAnimations();
        }
        binding = null;
        //viewModel.clearData();
    }

}