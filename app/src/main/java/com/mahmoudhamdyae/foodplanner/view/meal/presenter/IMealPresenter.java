package com.mahmoudhamdyae.foodplanner.view.meal.presenter;

import com.mahmoudhamdyae.foodplanner.model.Meal;

public interface IMealPresenter {

    void getMealById(String mealId);

    void getFavMeals();
    void addMealToFav(Meal meal);
    void removeMealFromFav(Meal meal);
    Boolean hasUser();
}
