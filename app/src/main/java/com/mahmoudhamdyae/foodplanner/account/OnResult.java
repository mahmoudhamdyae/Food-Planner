package com.mahmoudhamdyae.foodplanner.account;

import android.content.Intent;

public interface OnResult {

    void onAuthSuccess();
    void onGoogleAuthSuccess(Intent signInIntent);
    void onFailure(String errorMsg);
}
