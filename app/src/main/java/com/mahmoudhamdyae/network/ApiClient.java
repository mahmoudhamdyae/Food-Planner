package com.mahmoudhamdyae.network;

import android.util.Log;

import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;
import com.mahmoudhamdyae.foodplanner.model.SearchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient implements RemoteSource {

    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    public static final String TAG = "Api_Client";
    private List<Meal> meals;

    private static ApiClient client = null;

    private ApiClient() {
        meals = new ArrayList<>();
    }

    public static ApiClient getInstance() {
        if (client == null) {
            client = new ApiClient();
        }
        return client;
    }

    @Override
    public void getMeals(NetworkCallback networkCallback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<MealsResponse> call = apiService.getMeals();
        call.enqueue(new Callback<MealsResponse>() {

            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: CallBack " + response.raw() + response.body().getCategories());
                    networkCallback.onSuccessResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: CallBack");
                networkCallback.onFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void searchMeal(String name, NetworkCallback networkCallback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<SearchResponse> call = apiService.searchMealsByName(name);
        call.enqueue(new Callback<SearchResponse>() {

            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: CallBack " + response.raw() + response.body().getMeals());
                    networkCallback.onSuccessResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: CallBack");
                networkCallback.onFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
