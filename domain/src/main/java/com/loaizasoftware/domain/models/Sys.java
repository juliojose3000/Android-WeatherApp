package com.loaizasoftware.domain.models;

import com.loaizasoftware.core.utils.TimeUtils;

public class Sys {
    public int type;
    //public int id;
    public String country;
    public long sunrise;
    public long sunset;

    public String getSunriseTime() {
        return "Sunrise: "+TimeUtils.convertUnixToReadableTime(sunrise);
    }

    public String getSunsetTime() {
        return "Sunset: "+TimeUtils.convertUnixToReadableTime(sunset);
    }

}
