package com.mahmoudhamdyae.network;

public interface RemoteSource {

    void getCategories(NetworkCallback networkCallback);
    void searchMeal(String name, NetworkCallback networkCallback);
    void getMealOfTheDay(NetworkCallback networkCallback);
}
