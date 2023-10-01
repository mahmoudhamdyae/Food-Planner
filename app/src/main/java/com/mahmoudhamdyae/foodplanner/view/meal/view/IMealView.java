package com.mahmoudhamdyae.foodplanner.view.meal.view;

import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;

import java.util.List;

public interface IMealView {

    void showData(List<Meal> meals);

    void onGetMealSuccess(MealsResponse mealsResponse);
    void onGetMealFail(String errorMsg);
}
