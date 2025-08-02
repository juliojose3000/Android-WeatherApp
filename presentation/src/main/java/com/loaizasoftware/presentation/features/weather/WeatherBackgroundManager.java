package com.loaizasoftware.presentation.features.weather;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;

import com.loaizasoftware.domain.models.WeatherType;
import com.loaizasoftware.presentation.R;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * {@code WeatherBackgroundManager} is responsible for dynamically updating the background visuals
 * of the weather screen based on the current weather condition. It handles background gradients
 * and layered animations like sun rays, clouds, rain, snow, storms, and fog.
 */
public class WeatherBackgroundManager {

    private Context context;
    private ViewGroup backgroundContainer;
    private ImageView particleLayer;
    private View gradientOverlay;

    /**
     * Constructs a new {@code WeatherBackgroundManager}.
     *
     * @param context the application context
     * @param backgroundContainer the parent layout to which animated weather backgrounds will be added
     */
    public WeatherBackgroundManager(Context context, ViewGroup backgroundContainer) {
        this.context = context;
        this.backgroundContainer = backgroundContainer;
        setupLayers();
    }

    /**
     * Initializes and adds the gradient and particle animation layers to the container.
     */
    private void setupLayers() {
        // Gradient overlay setup
        gradientOverlay = new View(context);
        gradientOverlay.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        backgroundContainer.addView(gradientOverlay, 0);

        // Particle animation layer setup
        particleLayer = new ImageView(context);
        particleLayer.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        particleLayer.setScaleType(ImageView.ScaleType.MATRIX);
        backgroundContainer.addView(particleLayer, 1);
    }

    /**
     * Applies a weather-specific background and animation.
     *
     * @param weatherCondition a string representing the current weather condition (e.g., "sunny", "rain")
     */
    public void setWeatherBackground(String weatherCondition) {
        WeatherType type = WeatherType.fromCondition(weatherCondition);

        switch (type) {
            case SUNNY:
                setSunnyBackground();
                break;
            case CLOUDY:
                setCloudyBackground();
                break;
            case RAINY:
                setRainyBackground();
                break;
            case SNOWY:
                setSnowyBackground();
                break;
            case STORMY:
                setStormyBackground();
                break;
            case FOGGY:
                setFoggyBackground();
                break;
            default:
                setDefaultBackground();
                break;
        }
    }

    /**
     * Sets and animates a sunny background with sun rays.
     */
    private void setSunnyBackground() {
        int[] colors = {
                ContextCompat.getColor(context, R.color.sunny_start),
                ContextCompat.getColor(context, R.color.sunny_end)
        };
        setGradientBackground(colors);
        startSunRaysAnimation();
    }

    /**
     * Sets and animates a cloudy background with floating cloud effects.
     */
    private void setCloudyBackground() {
        int[] colors = {
                ContextCompat.getColor(context, R.color.cloudy_start),
                ContextCompat.getColor(context, R.color.cloudy_end)
        };
        setGradientBackground(colors);
        startCloudAnimation();
    }

    /**
     * Sets and animates a rainy background with falling raindrops.
     */
    private void setRainyBackground() {
        int[] colors = {
                ContextCompat.getColor(context, R.color.rainy_start),
                ContextCompat.getColor(context, R.color.rainy_end)
        };
        setGradientBackground(colors);
        startRainAnimation();
    }

    /**
     * Sets and animates a snowy background with falling snowflakes.
     */
    private void setSnowyBackground() {
        int[] colors = {
                ContextCompat.getColor(context, R.color.snowy_start),
                ContextCompat.getColor(context, R.color.snowy_end)
        };
        setGradientBackground(colors);
        startSnowAnimation();
    }

    /**
     * Sets and animates a stormy background with lightning effects.
     */
    private void setStormyBackground() {
        int[] colors = {
                ContextCompat.getColor(context, R.color.stormy_start),
                ContextCompat.getColor(context, R.color.stormy_end)
        };
        setGradientBackground(colors);
        startStormAnimation();
    }

    /**
     * Sets and animates a foggy background with light fog effects.
     */
    private void setFoggyBackground() {
        int[] colors = {
                ContextCompat.getColor(context, R.color.foggy_start),
                ContextCompat.getColor(context, R.color.foggy_end)
        };
        setGradientBackground(colors);
        startFogAnimation();
    }

    /**
     * Applies a fallback/default background when no weather condition matches.
     */
    private void setDefaultBackground() {
        int[] colors = {
                ContextCompat.getColor(context, R.color.default_start),
                ContextCompat.getColor(context, R.color.default_end)
        };
        setGradientBackground(colors);
    }

    /**
     * Smoothly transitions the gradient background to a new color array.
     *
     * @param colors array of two gradient colors
     */
    private void setGradientBackground(int[] colors) {
        GradientDrawable gradient = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                colors
        );

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

    /**
     * Animates rotating sun rays and a pulsing alpha effect for sunny weather.
     */
    private void startSunRaysAnimation() {
        particleLayer.setImageResource(R.drawable.sun_rays);

        ObjectAnimator rotation = ObjectAnimator.ofFloat(particleLayer, "rotation", 0f, 360f);
        rotation.setDuration(20000);
        rotation.setRepeatCount(ValueAnimator.INFINITE);
        rotation.setInterpolator(new LinearInterpolator());
        rotation.start();

        ObjectAnimator pulse = ObjectAnimator.ofFloat(particleLayer, "alpha", 0.3f, 0.7f);
        pulse.setDuration(3000);
        pulse.setRepeatCount(ValueAnimator.INFINITE);
        pulse.setRepeatMode(ValueAnimator.REVERSE);
        pulse.start();
    }

    /**
     * Animates a floating cloud effect for cloudy weather.
     */
    private void startCloudAnimation() {
        particleLayer.setImageResource(R.drawable.floating_clouds);

        ObjectAnimator translateX = ObjectAnimator.ofFloat(particleLayer, "translationX", -100f, 100f);
        translateX.setDuration(8000);
        translateX.setRepeatCount(ValueAnimator.INFINITE);
        translateX.setRepeatMode(ValueAnimator.REVERSE);
        translateX.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator translateY = ObjectAnimator.ofFloat(particleLayer, "translationY", -50f, 50f);
        translateY.setDuration(6000);
        translateY.setRepeatCount(ValueAnimator.INFINITE);
        translateY.setRepeatMode(ValueAnimator.REVERSE);
        translateY.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet cloudSet = new AnimatorSet();
        cloudSet.playTogether(translateX, translateY);
        cloudSet.start();
    }

    /**
     * Animates falling raindrops and a light horizontal sway.
     */
    private void startRainAnimation() {
        particleLayer.setImageResource(R.drawable.rain_drops);

        ObjectAnimator rainFall = ObjectAnimator.ofFloat(
                particleLayer, "translationY", -backgroundContainer.getHeight(), backgroundContainer.getHeight()
        );
        rainFall.setDuration(1500);
        rainFall.setRepeatCount(ValueAnimator.INFINITE);
        rainFall.setInterpolator(new LinearInterpolator());

        ObjectAnimator windEffect = ObjectAnimator.ofFloat(particleLayer, "translationX", 0f, 30f);
        windEffect.setDuration(1500);
        windEffect.setRepeatCount(ValueAnimator.INFINITE);
        windEffect.setInterpolator(new LinearInterpolator());

        AnimatorSet rainSet = new AnimatorSet();
        rainSet.playTogether(rainFall, windEffect);
        rainSet.start();
    }

    /**
     * Animates gentle snowfall with horizontal sway.
     */
    private void startSnowAnimation() {
        particleLayer.setImageResource(R.drawable.snow_flakes);

        ObjectAnimator snowFall = ObjectAnimator.ofFloat(
                particleLayer, "translationY", -backgroundContainer.getHeight(), backgroundContainer.getHeight()
        );
        snowFall.setDuration(8000);
        snowFall.setRepeatCount(ValueAnimator.INFINITE);
        snowFall.setInterpolator(new LinearInterpolator());

        ObjectAnimator sway = ObjectAnimator.ofFloat(particleLayer, "translationX", -20f, 20f);
        sway.setDuration(3000);
        sway.setRepeatCount(ValueAnimator.INFINITE);
        sway.setRepeatMode(ValueAnimator.REVERSE);
        sway.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet snowSet = new AnimatorSet();
        snowSet.playTogether(snowFall, sway);
        snowSet.start();
    }

    /**
     * Animates a stormy background with lightning flickers and pulsing effects.
     * Blinks once, waits 5 seconds, then repeats.
     */
    private void startStormAnimation() {
        particleLayer.setImageResource(R.drawable.lightning_effects);

        // Create the blink animation (single cycle)
        ObjectAnimator lightning = ObjectAnimator.ofFloat(particleLayer, "alpha", 0f, 1f, 0f);
        lightning.setDuration(300);
        lightning.setRepeatCount(0); // No repeat - single blink

        ObjectAnimator backgroundPulse = ObjectAnimator.ofFloat(gradientOverlay, "alpha", 1f, 0.8f, 1f);
        backgroundPulse.setDuration(300);
        backgroundPulse.setRepeatCount(0); // No repeat - single pulse

        // Create the animation set for one cycle
        AnimatorSet stormSet = new AnimatorSet();
        stormSet.playTogether(lightning, backgroundPulse);

        // Add listener to restart after 5 seconds
        stormSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Wait 5 seconds then restart
                particleLayer.postDelayed(() -> startStormAnimation(), 5000);
            }
        });

        stormSet.start();
    }

    /**
     * Animates a fog overlay with movement and changing opacity.
     */
    private void startFogAnimation() {
        particleLayer.setImageResource(R.drawable.fog_overlay);

        ObjectAnimator fogMove = ObjectAnimator.ofFloat(particleLayer, "translationX", -50f, 50f);
        fogMove.setDuration(10000);
        fogMove.setRepeatCount(ValueAnimator.INFINITE);
        fogMove.setRepeatMode(ValueAnimator.REVERSE);
        fogMove.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator fogOpacity = ObjectAnimator.ofFloat(particleLayer, "alpha", 0.3f, 0.6f);
        fogOpacity.setDuration(4000);
        fogOpacity.setRepeatCount(ValueAnimator.INFINITE);
        fogOpacity.setRepeatMode(ValueAnimator.REVERSE);

        AnimatorSet fogSet = new AnimatorSet();
        fogSet.playTogether(fogMove, fogOpacity);
        fogSet.start();
    }

    /**
     * Stops all background and particle animations.
     */
    public void stopAllAnimations() {
        particleLayer.clearAnimation();
        gradientOverlay.clearAnimation();
    }
}
