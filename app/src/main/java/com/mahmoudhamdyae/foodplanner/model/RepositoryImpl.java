package com.mahmoudhamdyae.foodplanner.model;

import com.mahmoudhamdyae.foodplanner.db.LocalDataSource;
import com.mahmoudhamdyae.foodplanner.network.NetworkCallback;
import com.mahmoudhamdyae.foodplanner.network.RemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl repo = null;

    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;

    private RepositoryImpl(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public static RepositoryImpl getInstance(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        if (repo == null) {
            repo = new RepositoryImpl(remoteDataSource, localDataSource);
        }
        return repo;
    }

    // Remote Data Source

    @Override
    public void getCategories(NetworkCallback networkCallback) {
        remoteDataSource.getCategories(networkCallback);
    }

    @Override
    public void getMealOfTheDay(NetworkCallback networkCallback) {
        remoteDataSource.getMealOfTheDay(networkCallback);
    }

    @Override
    public void searchMeal(String name, NetworkCallback networkCallback) {
        remoteDataSource.searchMeal(name, networkCallback);
    }

    @Override
    public void getIngredients(NetworkCallback networkCallback) {
        remoteDataSource.getIngredients(networkCallback);
    }

    @Override
    public void getMealsByArea(String area, NetworkCallback networkCallback) {
        remoteDataSource.getMealsByArea(area, networkCallback);
    }

    @Override
    public void getMealsByCategory(String category, NetworkCallback networkCallback) {
        remoteDataSource.getMealsByCategory(category, networkCallback);
    }

    @Override
    public void getMealsByIngredient(String ingredient, NetworkCallback networkCallback) {
        remoteDataSource.getMealsByIngredient(ingredient, networkCallback);
    }

    @Override
    public void getMealById(String mealId, NetworkCallback networkCallback) {
        remoteDataSource.getMealById(mealId, networkCallback);
    }

    // Local Data Source

    @Override
    public Flowable<List<Meal>> getFavMeals() {
        return localDataSource.getFavMeals();
    }

    @Override
    public void addMealToFav(Meal meal, NetworkCallback networkCallback) {
        localDataSource.addMealToFav(meal);
        remoteDataSource.addMealToFav(meal, networkCallback);
    }

    @Override
    public void removeMealFromFav(Meal meal, NetworkCallback networkCallback) {
        if (meal.getDay() != null) {
            // Plan and Fav
            meal.setFavourite(false);
            localDataSource.addMealToFav(meal);
            remoteDataSource.addMealToFav(meal, networkCallback);
        } else {
            // Fav only
            localDataSource.removeMealFromFav(meal);
            remoteDataSource.removeMealFromFav(meal, networkCallback);
        }
    }

    @Override
    public void removeMealFromPlan(Meal meal, NetworkCallback networkCallback) {
        if (meal.getFavourite()) {
            // Plan and Fav
            meal.setDay(null);
            localDataSource.addMealToFav(meal);
            remoteDataSource.addMealToFav(meal, networkCallback);
        } else {
            // Plan only
            localDataSource.removeMealFromFav(meal);
            remoteDataSource.removeMealFromFav(meal, networkCallback);
        }
    }

    @Override
    public void removeAllMealsFromFav() {
        localDataSource.removeAllMealsFromFav();
    }

    @Override
    public void getFavMealsFromFirebase(NetworkCallback networkCallback) {
        remoteDataSource.getFavMeals(networkCallback);
    }
}
