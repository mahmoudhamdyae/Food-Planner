package com.mahmoudhamdyae.network;

import com.mahmoudhamdyae.foodplanner.model.MealsResponse;
import com.mahmoudhamdyae.foodplanner.model.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("categories.php")
    Call<MealsResponse> getMeals();

    @GET("search.php")
    Call<SearchResponse> searchMealsByName(@Query("s") String name);
}
