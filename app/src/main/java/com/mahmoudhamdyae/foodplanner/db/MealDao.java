package com.mahmoudhamdyae.foodplanner.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mahmoudhamdyae.foodplanner.model.Meal;

import java.util.List;

@Dao
public interface MealDao {

    @Query("SELECT * FROM meal_table")
    LiveData<List<Meal>> observeFavMeal();

    @Query("SELECT * FROM meal_table")
    List<Meal> getFavMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMealToFav(Meal... meal);

    @Delete
    void removeMealFromFav(Meal meal);

    @Query("DELETE FROM meal_table")
    void removeAllMealsFromFav();
}
