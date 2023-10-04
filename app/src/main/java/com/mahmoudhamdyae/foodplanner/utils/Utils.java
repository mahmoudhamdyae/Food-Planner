package com.mahmoudhamdyae.foodplanner.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

public class Utils {

    public static int getNoOfColumns(@NonNull Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) Math.ceil(screenWidthDp / 185f);
    }
}
