package com.mahmoudhamdyae.foodplanner.view.search.meals.view;

import com.mahmoudhamdyae.foodplanner.model.MealsResponse;

public interface IMealsView {

    void onGetMealsSuccess(MealsResponse mealsResponse);

    void onGetMealsFail(String errorMsg);
}
