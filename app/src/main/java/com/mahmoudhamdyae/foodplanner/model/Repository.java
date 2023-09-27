package com.mahmoudhamdyae.foodplanner.model;

import com.mahmoudhamdyae.network.NetworkCallback;

public interface Repository {

    void getCategories(NetworkCallback networkCallback);

    void searchMeal(String name, NetworkCallback networkCallback);

    void getMealOfTheDay(NetworkCallback networkCallback);
}
