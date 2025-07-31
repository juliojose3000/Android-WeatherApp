package com.loaizasoftware.data.local.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.loaizasoftware.data.local.dao.WeatherDao;
import com.loaizasoftware.data.local.entities.WeatherEntity;

@Database(
        entities = {WeatherEntity.class},
        version = 1,
        exportSchema = false
)
//@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "app_database";

    // Abstract method to get DAO
    public abstract WeatherDao weatherDao();

    // Singleton pattern to get database instance
    public static AppDatabase getInstance(Context context) {
        synchronized (AppDatabase.class) {
            return Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME
                    )
                    .fallbackToDestructiveMigration() // Use carefully in production
                    .build();

        }
    }

}