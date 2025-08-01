package com.loaizasoftware.data.utils;

import android.content.Context;

import com.loaizasoftware.core.utils.NetworkUtils;
import com.loaizasoftware.domain.util.NetworkHandler;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class NetworkHandlerImpl implements NetworkHandler {
    private final Context context;

    @Inject
    public NetworkHandlerImpl(@ApplicationContext Context context) {
        this.context = context;
    }

    @Override
    public boolean isNetworkAvailable() {
        return NetworkUtils.isInternetAvailable(context);
    }
}

