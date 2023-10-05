package com.mahmoudhamdyae.foodplanner.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.mahmoudhamdyae.foodplanner.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LocalDataSourceImpl implements LocalDataSource {

    public static final String TAG = "LocalDataSource";

    private final MealDao dao;
    private final Flowable<List<Meal>> favMeals;

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
    public Flowable<List<Meal>> getFavMeals() {
        return favMeals.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void addMealToFav(Meal... meal) {
        dao.addMealToFav(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.i(TAG, "addMealToFav: " + meal[0].getName()),
                        error -> {
                            Log.i(TAG, "addMealToFav error: " + error.getMessage());
                            error.printStackTrace();
                        }
                );
    }

    @Override
    public void removeMealFromFav(Meal meal) {
        dao.removeMealFromFav(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.i(TAG, "removeMealFromFav"),
                        error -> {
                            Log.i(TAG, "removeMealFromFav error: " + error.getMessage());
                            error.printStackTrace();
                        }
                );
    }

    @Override
    public void removeAllMealsFromFav() {
        dao.removeAllMealsFromFav().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.i(TAG, "removeAllMealsFromFav"),
                        error -> {
                            Log.i(TAG, "removeAllMealsFromFav error: " + error.getMessage());
                            error.printStackTrace();
                        }
                );
    }
}
