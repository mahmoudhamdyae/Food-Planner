package com.mahmoudhamdyae.foodplanner.db;

import androidx.lifecycle.LiveData;

import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.view.meal.view.IMealView;

import java.util.List;

public interface LocalDataSource {

    LiveData<List<Meal>> observeFavMeals();
    void getFavMeals(IMealView view);
    void addMealToFav(Meal meal);
    void removeMealFromFav(Meal meal);
}
