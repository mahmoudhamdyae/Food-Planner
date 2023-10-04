package com.mahmoudhamdyae.foodplanner.view.auth.view;

import android.content.Intent;

public interface IAuthView {

    void onAuthSuccess();
    void onGoogleAuthSuccess(Intent signInIntent);
    void onFailure(String errorMsg);
}
