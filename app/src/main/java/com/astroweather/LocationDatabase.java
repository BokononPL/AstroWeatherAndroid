package com.astroweather;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Location.class}, version = 2)
public abstract class LocationDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();
    private static volatile LocationDatabase INSTANCE;

    public static LocationDatabase getInstance(final Context context) {
        if(INSTANCE == null) {
            synchronized (LocationDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            LocationDatabase.class,"student_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}