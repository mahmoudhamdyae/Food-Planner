package com.mahmoudhamdyae.foodplanner.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mahmoudhamdyae.foodplanner.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealDao {

    @Query("SELECT * FROM meal_table")
    Flowable<List<Meal>> observeFavMeal();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addMealToFav(Meal... meal);

    @Delete
    Completable removeMealFromFav(Meal meal);

    @Query("DELETE FROM meal_table")
    Completable removeAllMealsFromFav();
}
