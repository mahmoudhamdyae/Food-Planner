package com.mahmoudhamdyae.foodplanner.db;

import com.mahmoudhamdyae.foodplanner.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface LocalDataSource {

    Flowable<List<Meal>> getFavMeals();
    void addMealToFav(Meal... meal);
    void removeMealFromFav(Meal meal);
    void removeAllMealsFromFav();
}
