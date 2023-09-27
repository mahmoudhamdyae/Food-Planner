package com.mahmoudhamdyae.network;

public interface RemoteSource {

    void getMeals(NetworkCallback networkCallback);
    void searchMeal(String name, NetworkCallback networkCallback);
}
