package com.loaizasoftware.data.local.converters;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loaizasoftware.domain.models.Weather;

import java.lang.reflect.Type;
import java.util.List;


/**
 * Type converters for Room database to handle complex data types.
 *
 * This class provides conversion methods between Java objects and their JSON string
 * representations for storage in SQLite database. Room requires primitive types for
 * database columns, so complex objects like Lists must be converted to strings.
 *
 * The converters use Gson library for JSON serialization and deserialization,
 * providing a reliable way to store and retrieve complex data structures while
 * maintaining data integrity.
 *
 */
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
