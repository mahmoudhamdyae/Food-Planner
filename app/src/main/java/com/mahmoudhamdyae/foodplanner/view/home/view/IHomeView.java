package com.mahmoudhamdyae.foodplanner.view.home.view;

import com.mahmoudhamdyae.foodplanner.model.CategoryResponse;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;

public interface IHomeView {

    void onGetMealsSuccess(CategoryResponse categoryResponse);

    void onGetMealOfTheDaySuccess(MealsResponse mealsResponse);

    void onNetworkFail(String errorMsg);
}
