package com.mahmoudhamdyae.foodplanner.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mahmoudhamdyae.foodplanner.model.Meal;

@Database(entities = {Meal.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance = null;

    public abstract MealDao getDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "meals_db")
                    .build();
        }
        return instance;
    }
}
