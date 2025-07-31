package com.loaizasoftware.presentation.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;

import com.loaizasoftware.presentation.R;

public class WeatherBackgroundManager {

    private Context context;
    private ViewGroup backgroundContainer;
    private ImageView particleLayer;
    private View gradientOverlay;

    public WeatherBackgroundManager(Context context, ViewGroup backgroundContainer) {
        this.context = context;
        this.backgroundContainer = backgroundContainer;
        setupLayers();
    }

    private void setupLayers() {
        // Create gradient overlay
        gradientOverlay = new View(context);
        ViewGroup.LayoutParams gradientParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        gradientOverlay.setLayoutParams(gradientParams);
        backgroundContainer.addView(gradientOverlay, 0);

        // Create particle layer for animations
        particleLayer = new ImageView(context);
        ViewGroup.LayoutParams particleParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        particleLayer.setLayoutParams(particleParams);
        particleLayer.setScaleType(ImageView.ScaleType.MATRIX);
        backgroundContainer.addView(particleLayer, 1);
    }

    public void setWeatherBackground(String weatherCondition) {
        String condition = weatherCondition.toLowerCase();

        if (condition.contains("clear") || condition.contains("sunny")) {
            setSunnyBackground();
        } else if (condition.contains("clouds")) {
            setCloudyBackground();
        } else if (condition.contains("rain") || condition.contains("drizzle")) {
            setRainyBackground();
        } else if (condition.contains("snow")) {
            setSnowyBackground();
        } else if (condition.contains("storm") || condition.contains("thunder")) {
            setStormyBackground();
        } else if (condition.contains("fog") || condition.contains("mist")) {
            setFoggyBackground();
        } else {
            setDefaultBackground();
        }
    }

    private void setSunnyBackground() {
        // Sunny gradient colors
        int[] colors = {
                ContextCompat.getColor(context, R.color.sunny_start2),
                ContextCompat.getColor(context, R.color.sunny_end2)
        };
        setGradientBackground(colors);

        // Sun rays animation
        startSunRaysAnimation();
    }

    private void setCloudyBackground() {
        int[] colors = {
                ContextCompat.getColor(context, R.color.cloudy_start),
                ContextCompat.getColor(context, R.color.cloudy_end)
        };
        setGradientBackground(colors);

        // Floating clouds animation
        startCloudAnimation();
    }

    private void setRainyBackground() {
        int[] colors = {
                ContextCompat.getColor(context, R.color.rainy_start),
                ContextCompat.getColor(context, R.color.rainy_end)
        };
        setGradientBackground(colors);

        // Rain drops animation
        startRainAnimation();
    }

    private void setSnowyBackground() {
        int[] colors = {
                ContextCompat.getColor(context, R.color.snowy_start),
                ContextCompat.getColor(context, R.color.snowy_end)
        };
        setGradientBackground(colors);

        // Snowfall animation
        startSnowAnimation();
    }

    private void setStormyBackground() {
        int[] colors = {
                ContextCompat.getColor(context, R.color.stormy_start),
                ContextCompat.getColor(context, R.color.stormy_end)
        };
        setGradientBackground(colors);

        // Lightning and storm animation
        startStormAnimation();
    }

    private void setFoggyBackground() {
        int[] colors = {
                ContextCompat.getColor(context, R.color.foggy_start),
                ContextCompat.getColor(context, R.color.foggy_end)
        };
        setGradientBackground(colors);

        // Fog animation
        startFogAnimation();
    }

    private void setDefaultBackground() {
        int[] colors = {
                ContextCompat.getColor(context, R.color.default_start),
                ContextCompat.getColor(context, R.color.default_end)
        };
        setGradientBackground(colors);
    }

    private void setGradientBackground(int[] colors) {
        GradientDrawable gradient = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                colors
        );

        // Animate background transition
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(gradientOverlay, "alpha", 1f, 0f);
        fadeOut.setDuration(300);

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(gradientOverlay, "alpha", 0f, 1f);
        fadeIn.setDuration(300);

        fadeOut.addListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animation) {}

            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                gradientOverlay.setBackground(gradient);
                fadeIn.start();
            }

            @Override
            public void onAnimationCancel(android.animation.Animator animation) {}

            @Override
            public void onAnimationRepeat(android.animation.Animator animation) {}
        });

        fadeOut.start();
    }

    private void startSunRaysAnimation() {
        particleLayer.setImageResource(R.drawable.sun_rays);

        ObjectAnimator rotation = ObjectAnimator.ofFloat(particleLayer, "rotation", 0f, 360f);
        rotation.setDuration(20000);
        rotation.setRepeatCount(ValueAnimator.INFINITE);
        rotation.setInterpolator(new LinearInterpolator());
        rotation.start();

        // Pulsing effect
        ObjectAnimator pulse = ObjectAnimator.ofFloat(particleLayer, "alpha", 0.3f, 0.7f);
        pulse.setDuration(3000);
        pulse.setRepeatCount(ValueAnimator.INFINITE);
        pulse.setRepeatMode(ValueAnimator.REVERSE);
        pulse.start();
    }

    private void startCloudAnimation() {
        particleLayer.setImageResource(R.drawable.floating_clouds);

        // Horizontal floating motion
        ObjectAnimator translateX = ObjectAnimator.ofFloat(
                particleLayer, "translationX", -100f, 100f
        );
        translateX.setDuration(8000);
        translateX.setRepeatCount(ValueAnimator.INFINITE);
        translateX.setRepeatMode(ValueAnimator.REVERSE);
        translateX.setInterpolator(new AccelerateDecelerateInterpolator());

        // Vertical floating motion
        ObjectAnimator translateY = ObjectAnimator.ofFloat(
                particleLayer, "translationY", -50f, 50f
        );
        translateY.setDuration(6000);
        translateY.setRepeatCount(ValueAnimator.INFINITE);
        translateY.setRepeatMode(ValueAnimator.REVERSE);
        translateY.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet cloudSet = new AnimatorSet();
        cloudSet.playTogether(translateX, translateY);
        cloudSet.start();
    }

    private void startRainAnimation() {
        particleLayer.setImageResource(R.drawable.rain_drops);

        // Rain falling animation
        ObjectAnimator rainFall = ObjectAnimator.ofFloat(
                particleLayer, "translationY", -backgroundContainer.getHeight(),
                backgroundContainer.getHeight()
        );
        rainFall.setDuration(1500);
        rainFall.setRepeatCount(ValueAnimator.INFINITE);
        rainFall.setInterpolator(new LinearInterpolator());

        // Slight horizontal movement for realistic effect
        ObjectAnimator windEffect = ObjectAnimator.ofFloat(
                particleLayer, "translationX", 0f, 30f
        );
        windEffect.setDuration(1500);
        windEffect.setRepeatCount(ValueAnimator.INFINITE);
        windEffect.setInterpolator(new LinearInterpolator());

        AnimatorSet rainSet = new AnimatorSet();
        rainSet.playTogether(rainFall, windEffect);
        rainSet.start();
    }

    private void startSnowAnimation() {
        particleLayer.setImageResource(R.drawable.snow_flakes);

        // Gentle snowfall
        ObjectAnimator snowFall = ObjectAnimator.ofFloat(
                particleLayer, "translationY", -backgroundContainer.getHeight(),
                backgroundContainer.getHeight()
        );
        snowFall.setDuration(8000);
        snowFall.setRepeatCount(ValueAnimator.INFINITE);
        snowFall.setInterpolator(new LinearInterpolator());

        // Swaying motion
        ObjectAnimator sway = ObjectAnimator.ofFloat(
                particleLayer, "translationX", -20f, 20f
        );
        sway.setDuration(3000);
        sway.setRepeatCount(ValueAnimator.INFINITE);
        sway.setRepeatMode(ValueAnimator.REVERSE);
        sway.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet snowSet = new AnimatorSet();
        snowSet.playTogether(snowFall, sway);
        snowSet.start();
    }

    private void startStormAnimation() {
        particleLayer.setImageResource(R.drawable.lightning_effects);

        // Lightning flicker effect
        ObjectAnimator lightning = ObjectAnimator.ofFloat(particleLayer, "alpha", 0f, 1f, 0f);
        lightning.setDuration(200);
        lightning.setRepeatCount(ValueAnimator.INFINITE);
        lightning.setStartDelay(2000);

        // Background pulsing
        ObjectAnimator backgroundPulse = ObjectAnimator.ofFloat(gradientOverlay, "alpha", 0.8f, 1f);
        backgroundPulse.setDuration(100);
        backgroundPulse.setRepeatCount(ValueAnimator.INFINITE);
        backgroundPulse.setRepeatMode(ValueAnimator.REVERSE);

        AnimatorSet stormSet = new AnimatorSet();
        stormSet.playTogether(lightning, backgroundPulse);
        stormSet.start();
    }

    private void startFogAnimation() {
        particleLayer.setImageResource(R.drawable.fog_overlay);

        // Gentle fog movement
        ObjectAnimator fogMove = ObjectAnimator.ofFloat(
                particleLayer, "translationX", -50f, 50f
        );
        fogMove.setDuration(10000);
        fogMove.setRepeatCount(ValueAnimator.INFINITE);
        fogMove.setRepeatMode(ValueAnimator.REVERSE);
        fogMove.setInterpolator(new AccelerateDecelerateInterpolator());

        // Opacity variation
        ObjectAnimator fogOpacity = ObjectAnimator.ofFloat(particleLayer, "alpha", 0.3f, 0.6f);
        fogOpacity.setDuration(4000);
        fogOpacity.setRepeatCount(ValueAnimator.INFINITE);
        fogOpacity.setRepeatMode(ValueAnimator.REVERSE);

        AnimatorSet fogSet = new AnimatorSet();
        fogSet.playTogether(fogMove, fogOpacity);
        fogSet.start();
    }

    public void stopAllAnimations() {
        particleLayer.clearAnimation();
        gradientOverlay.clearAnimation();
    }

}