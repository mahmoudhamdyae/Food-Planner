package com.mahmoudhamdyae.foodplanner.network;

public interface RemoteDataSource {

    void getCategories(NetworkCallback networkCallback);
    void searchMeal(String name, NetworkCallback networkCallback);
    void getMealOfTheDay(NetworkCallback networkCallback);
    void getAreas(NetworkCallback networkCallback);
    void getIngredients(NetworkCallback networkCallback);
    void getCategoriesNames(NetworkCallback networkCallback);
    void getMealsByArea(String area, NetworkCallback networkCallback);
    void getMealsByCategory(String category, NetworkCallback networkCallback);
    void getMealsByIngredient(String ingredient, NetworkCallback networkCallback);

    void getMealById(String mealId, NetworkCallback networkCallback);
}
