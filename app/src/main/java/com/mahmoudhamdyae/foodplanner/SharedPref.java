package com.mahmoudhamdyae.foodplanner;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mahmoudhamdyae.foodplanner.model.Meal;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SharedPref {

    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String IS_FIRST_KEY = "isFirst";
    public static final String MEAL_KEY ="MealKey";
    public static final String DAY_KEY ="DayKey";
    public static final String YEAR_KEY ="YearKey";

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public SharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void setFirstTime() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_FIRST_KEY, false);
        editor.apply();
    }

    public Boolean isFirstTime() {
        return sharedPreferences.getBoolean(IS_FIRST_KEY, true);
    }

    public void setMeal(Meal meal) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MEAL_KEY, gson.toJson(meal));
        editor.apply();
        setDate();
    }

    public Meal getMeal() {
        String mealJson = sharedPreferences.getString(MEAL_KEY, null);
        return gson.fromJson(mealJson, Meal.class);
    }

    public void setDate() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(DAY_KEY, dayOfYear);
        editor.putInt(YEAR_KEY, year);
        editor.apply();
    }

    public Map<String, Integer> getDate() {
        int day = sharedPreferences.getInt(DAY_KEY, 0);
        int year = sharedPreferences.getInt(YEAR_KEY, 0);
        Map<String, Integer> date = new HashMap<>();
        date.put("day", day);
        date.put("year", year);
        return date;
    }
}
