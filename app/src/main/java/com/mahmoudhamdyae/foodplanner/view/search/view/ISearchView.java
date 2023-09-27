package com.mahmoudhamdyae.foodplanner.view.search.view;

import com.mahmoudhamdyae.foodplanner.model.MealsResponse;

public interface ISearchView {

    void onGetMealsSuccess(MealsResponse mealsResponse);

    void onGetMealsFail(String errorMsg);
}
