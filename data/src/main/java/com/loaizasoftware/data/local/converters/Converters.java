package com.loaizasoftware.data.local.converters;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loaizasoftware.domain.models.Weather;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static String fromWeatherList(List<Weather> list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<Weather> toWeatherList(String json) {
        Type type = new TypeToken<List<Weather>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
