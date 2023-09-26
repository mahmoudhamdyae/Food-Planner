package com.mahmoudhamdyae.network;

import com.mahmoudhamdyae.foodplanner.model.MealsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("categories.php")
    Call<MealsResponse> getMeals();
}
