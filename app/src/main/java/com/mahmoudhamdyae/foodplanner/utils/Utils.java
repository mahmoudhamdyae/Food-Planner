package com.mahmoudhamdyae.foodplanner.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.mahmoudhamdyae.foodplanner.R;

public class Utils {

    public static int getNoOfColumns(@NonNull Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) Math.ceil(screenWidthDp / 185f);
    }

    @StringRes
    public static int dayToString(int day) {
        switch (day) {
            case 0:
                return R.string.saturday;
            case 1:
                return R.string.sunday;
            case 2:
                return R.string.monday;
            case 3:
                return R.string.tuesday;
            case 4:
                return R.string.wednesday;
            case 5:
                return R.string.thursday;
            default:
                return R.string.friday;
        }
    }
}
