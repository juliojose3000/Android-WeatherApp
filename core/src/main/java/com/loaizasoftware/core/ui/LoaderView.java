package com.loaizasoftware.core.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.core.content.ContextCompat;

import com.loaizasoftware.core.R;

/**
 * This class allows us to show a loader screen in any activity
 */
public class LoaderView {

    private LinearLayout linearLayout;
    private LayoutInflater inflater;
    private View view;
    private boolean isShown = false;
    private Context context;

    public LoaderView(Context context) {
        this.context = context;
    }

    private void showLoaderOnView(Context context, int backgroundColor, boolean allowUserInteraction) {
        linearLayout = new LinearLayout(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.loader_screen, null);

        if (linearLayout != null) {
            linearLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
            linearLayout.setBackgroundColor(ContextCompat.getColor(context, backgroundColor));
            linearLayout.addView(view);
            linearLayout.setClickable(!allowUserInteraction);
            linearLayout.setGravity(Gravity.CENTER);
        }
    }

    public void showLoader() {
        if(isShown) return;
        isShown = true;
        showLoaderOnView(context, R.color.transparent, false);
    }

    public View showLoader(boolean allowUserInteraction) {
        isShown = true;
        showLoaderOnView(context, R.color.transparent, allowUserInteraction);
        return linearLayout;
    }

    public View showLoader(int backgroundColor, boolean allowUserInteraction) {
        isShown = true;
        showLoaderOnView(context, backgroundColor, allowUserInteraction);
        return linearLayout;
    }

    public void dismissLoader() {
        if (linearLayout != null) {
            linearLayout.setClickable(false);
            linearLayout.removeAllViews();
            linearLayout.setBackgroundResource(0);
        }
        isShown = false;
    }

    public void clearShown() {
        isShown = false;
    }

    public boolean isShown() {
        return isShown;
    }

    private static LoaderView loaderView;


    public static synchronized LoaderView getInstance(Context context) {
        if(loaderView == null) loaderView = new LoaderView(context);
        return loaderView;
    }
}
