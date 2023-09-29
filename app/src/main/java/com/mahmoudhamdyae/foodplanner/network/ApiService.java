package com.mahmoudhamdyae.foodplanner.network;

import com.mahmoudhamdyae.foodplanner.model.CategoryResponse;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("categories.php")
    Call<CategoryResponse> getMeals();

    @GET("search.php")
    Call<MealsResponse> searchMealsByName(@Query("s") String name);

    @GET("random.php")
    Call<MealsResponse> getMealOfTheDay();
}
