package com.loaizasoftware.trackforcechallenge.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.loaizasoftware.core.ui.LoaderView;
import com.loaizasoftware.presentation.fragments.WeatherFragment;
import com.loaizasoftware.trackforcechallenge.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final int LOCATION_PERMISSION_REQUEST = 1001;
    private LocationManager locationManager;
    private Geocoder geocoder;
    private LoaderView loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        loader = LoaderView.createInstance(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        geocoder = new Geocoder(this, Locale.getDefault());

        // Check if Geocoder is available
        if (!Geocoder.isPresent()) {
            Toast.makeText(this, "Geocoder not available", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestLocationPermission();
    }

    // Inside your Activity class
    public void showLoader() {
        runOnUiThread(() -> {
            if (loader != null && !loader.isShown()) {
                //hideKeyboard(); // Assuming you have this method in your Activity
                ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
                View loaderView = loader.showLoader(false);
                if (loaderView != null) {
                    rootView.addView(loaderView);
                }
            }
        });
    }

    private void navigateToWeatherFragment(double lat, double lon) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putDouble("lat", lat);
        bundle.putDouble("lon", lon);

        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(bundle);

        transaction.replace(R.id.root, fragment); // Replace existing fragment (if any)
        transaction.addToBackStack(null); // Optional: allows user to go back
        transaction.commit();

    }


    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show rationale and request permission again
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        LOCATION_PERMISSION_REQUEST);
            } else {
                // User denied with "Don't ask again"
                // Show dialog guiding them to settings
                showPermissionDeniedDialog();
            }
        } else {
            startLocationUpdates();
        }
    }

    private void showPermissionDeniedDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Permission Denied")
                .setMessage("Location permission has been denied permanently. Please enable it in app settings.")
                .setPositiveButton("Go to Settings", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                    finish(); // or handle gracefully
                })
                .setCancelable(false)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Location permission required", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void startLocationUpdates() {
        try {
            showLoader();

            // Request location updates
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    5000, // 5 seconds
                    10,   // 10 meters
                    this);

            // Also try network provider as backup
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    5000, 10, this);

            // Get last known location for immediate result
            /*Location lastKnownGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location lastKnownNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (lastKnownGPS != null) {
                getCityFromLocation(lastKnownGPS);
            } else if (lastKnownNetwork != null) {
                getCityFromLocation(lastKnownNetwork);
            }*/

        } catch (SecurityException e) {
            Log.e("LocationActivity", "Security exception: " + e.getMessage());
        }
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.d("LocationActivity", "Location changed: " + location.getLatitude() +
                ", " + location.getLongitude());

        getCityFromLocation(location);

        // Stop location updates after getting first location (optional)
        //locationManager.removeUpdates(this);
    }

    private void getCityFromLocation(Location location) {
        try {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            // Use Geocoder to get address from coordinates
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && !addresses.isEmpty()) {

                navigateToWeatherFragment(latitude, longitude);

            } else {
                Toast.makeText(this, "No address found for location", Toast.LENGTH_LONG).show();
                Log.w("LocationActivity", "No addresses returned from geocoder");
            }

        } catch (IOException e) {
            Log.e("LocationActivity", "Geocoder IOException: " + e.getMessage());
            Toast.makeText(this, "Error getting location address", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("LocationActivity", "Error in geocoding: " + e.getMessage());
        }
    }

}