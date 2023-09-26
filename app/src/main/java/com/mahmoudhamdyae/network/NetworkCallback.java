package com.mahmoudhamdyae.network;

import com.mahmoudhamdyae.foodplanner.model.MealsResponse;

public interface NetworkCallback {

    void onSuccessResult(MealsResponse mealsResponse);
    void onFailureResult(String errorMsg);
}
