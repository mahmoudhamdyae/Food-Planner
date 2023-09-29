package com.mahmoudhamdyae.foodplanner.model;

import androidx.lifecycle.LiveData;

import com.mahmoudhamdyae.foodplanner.network.NetworkCallback;
import com.mahmoudhamdyae.foodplanner.view.meal.view.IMealView;

import java.util.List;

public interface Repository {

    void getCategories(NetworkCallback networkCallback);
    void searchMeal(String name, NetworkCallback networkCallback);
    void getMealOfTheDay(NetworkCallback networkCallback);

    LiveData<List<Meal>> observeFavMeals();
    void getFavMeals(IMealView view);
    void addMealToFav(Meal meal);
    void removeMealFromFav(Meal meal);
}
