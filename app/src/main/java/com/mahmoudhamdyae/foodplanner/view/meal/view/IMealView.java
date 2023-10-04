package com.mahmoudhamdyae.foodplanner.view.meal.view;

import com.mahmoudhamdyae.foodplanner.model.Meal;

import java.util.List;

public interface IMealView {

    void showData(List<Meal> meals);

    void onGetMealSuccess(Meal meal);
    void onGetMealFail(String errorMsg);
}
