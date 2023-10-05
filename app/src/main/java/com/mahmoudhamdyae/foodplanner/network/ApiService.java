package com.mahmoudhamdyae.foodplanner.network;

import com.mahmoudhamdyae.foodplanner.model.CategoryResponse;
import com.mahmoudhamdyae.foodplanner.model.IngredientResponse;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("categories.php")
    Flowable<CategoryResponse> getCategories();

    @GET("search.php")
    Flowable<MealsResponse> searchMealsByName(@Query("s") String name);

    @GET("random.php")
    Flowable<MealsResponse> getMealOfTheDay();

    @GET("list.php?i=list")
    Flowable<IngredientResponse> getIngredients();

    @GET("filter.php") //
    Flowable<MealsResponse> getMealsByArea(@Query("a") String area);

    @GET("filter.php") //
    Flowable<MealsResponse> getMealsByCategory(@Query("c") String category);

    @GET("filter.php") //
    Flowable<MealsResponse> getMealsByIngredient(@Query("i") String ingredient);

    @GET("lookup.php")
    Flowable<MealsResponse> getMealById(@Query("i") String mealId);
}
