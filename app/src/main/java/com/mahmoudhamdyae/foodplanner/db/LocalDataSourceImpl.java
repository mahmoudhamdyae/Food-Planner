package com.mahmoudhamdyae.foodplanner.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.view.meal.view.IMealView;

import java.util.List;

public class LocalDataSourceImpl implements LocalDataSource {

    public static final String TAG = "Local_Data_Source";

    private final MealDao dao;
    private final LiveData<List<Meal>> favMeals;

    private static LocalDataSourceImpl instance = null;

    private LocalDataSourceImpl(@NonNull Context context) {
        AppDatabase db = AppDatabase.getInstance(context.getApplicationContext());
        dao = db.getDao();
        favMeals = dao.observeFavMeal();
    }

    public static LocalDataSource getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new LocalDataSourceImpl(context);
        }
        return instance;
    }

    @Override
    public LiveData<List<Meal>> observeFavMeals() {
        Log.i(TAG, "observeFavMeals");
        return favMeals;
    }

    @Override
    public void getFavMeals(IMealView view) {
        new Thread(() -> {
            Log.i(TAG, "getFavMeals");
            view.showData(dao.getFavMeals());
        }).start();
    }

    @Override
    public void addMealToFav(Meal meal) {
        new Thread(() -> {
            dao.addMealToFav(meal);
            Log.i(TAG, "addMealToFav");
        }).start();
    }

    @Override
    public void removeMealFromFav(Meal meal) {
        new Thread(() -> {
            dao.removeMealFromFav(meal);
            Log.i(TAG, "removeMealFromFav");
        }).start();
    }

    @Override
    public void removeAllMealsFromFav() {
        new Thread(() -> {
            dao.removeAllMealsFromFav();
            Log.i(TAG, "removeAllMealsFromFav");
        }).start();
    }
}
