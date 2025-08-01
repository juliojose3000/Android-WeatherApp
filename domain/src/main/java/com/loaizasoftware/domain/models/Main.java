package com.loaizasoftware.domain.models;

public class Main {
    public double temp;
    public double feels_like;
    public double temp_min;
    public double temp_max;
    public int pressure;
    public int humidity;
    public int sea_level;
    public int grnd_level;

    // Temperature methods - rounded to 1 decimal place
    public String getFormattedTemp() {
        return String.format("%.0f°", temp);
    }

    public String getFormattedFeelsLike() {
        return "Feels like "+String.format("%.0f°", feels_like);
    }

    public String getFormattedTempMinAndMax() {
        return "Max "+String.format("%.0f°", temp_max) + " / Min "+String.format("%.0f°", temp_min);
    }

    // Pressure methods - rounded to whole numbers with hPa unit
    public String getFormattedPressure() {
        return "Pressure: "+pressure + " hPa";
    }

    public String getFormattedSeaLevel() {
        return sea_level + " hPa";
    }

    public String getFormattedGroundLevel() {
        return grnd_level + " hPa";
    }

    // Humidity method - percentage symbol
    public String getFormattedHumidity() {
        return "Humidity: "+humidity + "%";
    }

}
