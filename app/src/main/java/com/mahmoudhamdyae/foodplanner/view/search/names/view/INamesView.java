package com.mahmoudhamdyae.foodplanner.view.search.names.view;

import com.mahmoudhamdyae.foodplanner.model.MealsResponse;

public interface INamesView {

    void onGetMealsSuccess(MealsResponse mealsResponse);

    void onGetMealsFail(String errorMsg);
}
