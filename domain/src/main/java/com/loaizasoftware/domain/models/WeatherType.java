package com.loaizasoftware.domain.models;

public enum WeatherType {
    SUNNY,
    CLOUDY,
    RAINY,
    SNOWY,
    STORMY,
    FOGGY,
    DEFAULT;

    public static WeatherType fromCondition(String condition) {
        String lower = condition.toLowerCase();

        if (lower.contains("clear") || lower.contains("sunny")) {
            return SUNNY;
        } else if (lower.contains("clouds")) {
            return CLOUDY;
        } else if (lower.contains("rain") || lower.contains("drizzle")) {
            return RAINY;
        } else if (lower.contains("snow")) {
            return SNOWY;
        } else if (lower.contains("storm") || lower.contains("thunder")) {
            return STORMY;
        } else if (lower.contains("fog") || lower.contains("mist")) {
            return FOGGY;
        } else {
            return DEFAULT;
        }
    }

    // This returns the word like "cloudy" instead of enum name
    public String toDisplayString() {
        switch (this) {
            case SUNNY: return "Sunny";
            case CLOUDY: return "Cloudy";
            case RAINY: return "Rainy";
            case SNOWY: return "Snowy";
            case STORMY: return "Stormy";
            case FOGGY: return "Foggy";
            default: return "Unknown";
        }
    }
}