package com.mahmoudhamdyae.foodplanner.network;

import com.mahmoudhamdyae.foodplanner.model.Meal;

public interface RemoteDataSource {

    void getCategories(NetworkCallback networkCallback);
    void searchMeal(String name, NetworkCallback networkCallback);
    void getMealOfTheDay(NetworkCallback networkCallback);
    void getIngredients(NetworkCallback networkCallback);
    void getMealsByArea(String area, NetworkCallback networkCallback);
    void getMealsByCategory(String category, NetworkCallback networkCallback);
    void getMealsByIngredient(String ingredient, NetworkCallback networkCallback);

    void getMealById(String mealId, NetworkCallback networkCallback);

    // Firebase Firestore
    void getFavMeals(NetworkCallback networkCallback);
    void addMealToFav(Meal meal, NetworkCallback networkCallback);
    void removeMealFromFav(Meal meal, NetworkCallback networkCallback);
}
