package com.mahmoudhamdyae.foodplanner.view.meal.view;

import com.mahmoudhamdyae.foodplanner.model.Meal;

public interface IMealView {
    void onGetMealSuccess(Meal meal);
    void onGetMealFail(String errorMsg);
}
