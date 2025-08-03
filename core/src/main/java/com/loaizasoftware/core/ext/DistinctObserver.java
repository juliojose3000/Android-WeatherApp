package com.loaizasoftware.core.ext;

import android.location.Location;

import androidx.lifecycle.Observer;

import com.loaizasoftware.core.utils.LocationUtils;

import java.util.Objects;

/**
 * A custom LiveData observer that only reacts to distinct values.
 * <p>
 * This is useful to avoid unnecessary UI updates or actions when the same value
 * is emitted repeatedly by LiveData. For general objects, it uses {@link Objects#equals(Object, Object)}.
 * For {@link Location}, it uses a custom comparator to allow more nuanced equality checks
 * (e.g., comparing timestamps or precision).
 *
 * @param <T> The type of data observed, which may include {@link Location}.
 */
public abstract class DistinctObserver<T> implements Observer<T> {

    /** Keeps track of the last observed value to detect duplicates */
    private T lastValue = null;

    /**
     * Called when the LiveData value changes. This implementation filters out
     * duplicate emissions (i.e., values that are logically the same).
     *
     * @param t The newly emitted value from LiveData
     */
    @Override
    public void onChanged(T t) {

        if (t instanceof Location) {
            // Use custom comparison for Location objects
            if (!LocationUtils.compare((Location) t, (Location) lastValue)) {
                lastValue = t;
                onNewValue(t);
            }
        } else {
            // Use standard equality check for all other objects
            if (!Objects.equals(t, lastValue)) {
                lastValue = t;
                onNewValue(t);
            }
        }
    }

    /**
     * Called only when a new, distinct value is received.
     *
     * @param t The new value that is different from the previous one
     */
    public abstract void onNewValue(T t);
}
