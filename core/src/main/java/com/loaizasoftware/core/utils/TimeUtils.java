package com.loaizasoftware.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    public static String convertUnixToReadableTime(long timestampSeconds) {
        var date = new Date(timestampSeconds * 1000); // Multiply to convert seconds → milliseconds

        // Set your desired format, e.g. "hh:mm a" → 03:45 PM
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.getDefault());

        // Set the timezone to your local or a specific one
        //sdf.setTimeZone(TimeZone.getDefault());

        return sdf.format(date);
    }
}
