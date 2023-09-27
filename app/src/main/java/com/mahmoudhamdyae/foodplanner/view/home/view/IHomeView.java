package com.mahmoudhamdyae.foodplanner.view.home.view;

import com.mahmoudhamdyae.foodplanner.model.MealsResponse;

public interface IHomeView {

    void onGetMealsSuccess(MealsResponse mealsResponse);

    void onGetMealsFail(String errorMsg);
}
