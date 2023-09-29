package com.mahmoudhamdyae.foodplanner.network;

public interface RemoteDataSource {

    void getCategories(NetworkCallback networkCallback);
    void searchMeal(String name, NetworkCallback networkCallback);
    void getMealOfTheDay(NetworkCallback networkCallback);
}
