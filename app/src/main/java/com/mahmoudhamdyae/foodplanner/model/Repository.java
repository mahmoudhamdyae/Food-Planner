package com.mahmoudhamdyae.foodplanner.model;

import com.mahmoudhamdyae.network.NetworkCallback;

public interface Repository {

    void getMeals(NetworkCallback networkCallback);

    void searchMeal(String name, NetworkCallback networkCallback);
}
