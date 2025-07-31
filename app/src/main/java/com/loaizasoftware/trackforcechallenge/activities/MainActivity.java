package com.loaizasoftware.trackforcechallenge.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.loaizasoftware.presentation.fragments.WeatherFragment;
import com.loaizasoftware.trackforcechallenge.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        WeatherFragment fragment = new WeatherFragment();
        transaction.replace(R.id.root, fragment); // Replace existing fragment (if any)
        transaction.addToBackStack(null); // Optional: allows user to go back
        transaction.commit();

    }

}