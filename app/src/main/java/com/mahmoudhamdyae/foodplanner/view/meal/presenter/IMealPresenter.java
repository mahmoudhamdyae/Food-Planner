package com.mahmoudhamdyae.foodplanner.view.meal.presenter;

import com.mahmoudhamdyae.foodplanner.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface IMealPresenter {

    void getMealById(String mealId);

    Flowable<List<Meal>> getFavMeals();
    void addMealToFav(Meal meal);
    void removeMealFromFav(Meal meal);
    Boolean hasUser();
}
