package com.mahmoudhamdyae.network;

public interface NetworkCallback {

    void onSuccessResult(Object object);
    void onFailureResult(String errorMsg);
}
