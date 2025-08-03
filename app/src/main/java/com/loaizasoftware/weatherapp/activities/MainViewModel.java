package com.loaizasoftware.weatherapp.activities;


import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Location> _location = new MutableLiveData<>();
    public final LiveData<Location> location = _location;

    public final MutableLiveData<Boolean> showLoader = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isLocationPermissionEnabled = new MutableLiveData<>();

    public void setLocation(Location location) {
        this._location.setValue(location);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        _location.setValue(null);
    }
}
