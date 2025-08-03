package com.loaizasoftware.core.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loaizasoftware.core.ui.LoaderView;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    // Inside your Activity class
    public void showLoader() {
        runOnUiThread(() -> {
            LoaderView loader = LoaderView.getInstance(this);
            if (loader != null && !loader.isShown()) {
                ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
                View loaderView = loader.showLoader(false);
                if (loaderView != null) {
                    rootView.addView(loaderView);
                }
            }
        });
    }

    public void dismissLoader() {
        LoaderView.getInstance(this).dismissLoader();
    }

}
