package com.loaizasoftware.core.utils;

import android.location.Location;

/**
 * Utility class for comparing {@link Location} objects with enhanced precision and logic.
 * <p>
 * This is especially useful when working with LiveData or location observers, where
 * frequent updates may carry the same location values with minimal or no changes.
 */
public class LocationUtils {

    /**
     * Compares two {@link Location} objects for logical equality.
     * <p>
     * The comparison includes:
     * <ul>
     *     <li>Latitude and longitude (exact match using {@link Double#compare})</li>
     *     <li>Accuracy (using {@link Float#compare})</li>
     *     <li>Altitude (exact match)</li>
     *     <li>Bearing (exact match)</li>
     *     <li>Speed (exact match)</li>
     *     <li>Timestamp difference must be under 5 minutes (300,000 ms)</li>
     * </ul>
     *
     * @param loc1 The first location object
     * @param loc2 The second location object
     * @return {@code true} if both locations are considered the same based on the defined criteria; {@code false} otherwise
     */
    public static boolean compare(Location loc1, Location loc2) {
        if (loc1 == null || loc2 == null) return false;

        // Latitude and Longitude must match exactly
        boolean sameCoordinates =
                Double.compare(loc1.getLatitude(), loc2.getLatitude()) == 0 &&
                        Double.compare(loc1.getLongitude(), loc2.getLongitude()) == 0;

        // Horizontal accuracy must match
        boolean sameAccuracy =
                Float.compare(loc1.getAccuracy(), loc2.getAccuracy()) == 0;

        // Optional detailed comparisons
        boolean sameAltitude = Double.compare(loc1.getAltitude(), loc2.getAltitude()) == 0;
        boolean sameBearing = Float.compare(loc1.getBearing(), loc2.getBearing()) == 0;
        boolean sameSpeed = Float.compare(loc1.getSpeed(), loc2.getSpeed()) == 0;

        // Timestamp must be within 5 minutes
        long timeDiffMillis = Math.abs(loc1.getTime() - loc2.getTime());
        boolean withinFiveMinutes = timeDiffMillis < (5 * 60 * 1000);

        return sameCoordinates &&
                sameAccuracy &&
                sameAltitude &&
                sameBearing &&
                sameSpeed &&
                withinFiveMinutes;
    }
}
