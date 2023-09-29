package com.mahmoudhamdyae.foodplanner.network;

public interface NetworkCallback {

    void onSuccessResult(Object object);
    void onFailureResult(String errorMsg);
}
