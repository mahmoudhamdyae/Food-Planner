package com.mahmoudhamdyae.foodplanner.network;

import com.mahmoudhamdyae.foodplanner.model.CategoryNamesResponse;
import com.mahmoudhamdyae.foodplanner.model.CategoryResponse;
import com.mahmoudhamdyae.foodplanner.model.AreaResponse;
import com.mahmoudhamdyae.foodplanner.model.IngredientResponse;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("categories.php")
    Call<CategoryResponse> getCategories();

    @GET("search.php")
    Call<MealsResponse> searchMealsByName(@Query("s") String name);

    @GET("random.php")
    Call<MealsResponse> getMealOfTheDay();

    @GET("list.php?a=list")
    Call<AreaResponse> getAreas();

    @GET("list.php?c=list")
    Call<CategoryNamesResponse> getCategoriesNames();

    @GET("list.php?i=list")
    Call<IngredientResponse> getIngredients();

    @GET("filter.php") //
    Call<MealsResponse> getMealsByArea(@Query("a") String area);

    @GET("filter.php") //
    Call<MealsResponse> getMealsByCategory(@Query("c") String category);

    @GET("filter.php") //
    Call<MealsResponse> getMealsByIngredient(@Query("i") String ingredient);
}
