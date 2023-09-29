package com.mahmoudhamdyae.foodplanner;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String Is_First_KEY = "isFirst";

    private Context context;

    public SharedPref(Context context) {
        this.context = context;
    }

    public void setFirstTime() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Is_First_KEY, false);
        editor.apply();
    }

    public Boolean isFirstTime() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Is_First_KEY, true);
    }
}
