package com.mahmoudhamdyae.foodplanner.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.mahmoudhamdyae.foodplanner.model.CategoryResponse;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSourceImpl implements RemoteDataSource {

    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    public static final String TAG = "Remote_Data_Source";

    private static RemoteDataSourceImpl client = null;
    private ApiService apiService;

    private RemoteDataSourceImpl() { }

    public static RemoteDataSource getInstance() {
        if (client == null) {
            client = new RemoteDataSourceImpl();
        }
        return client;
    }

    private void makeNetworkCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    @Override
    public void getCategories(NetworkCallback networkCallback) {
        makeNetworkCall();
        Call<CategoryResponse> call = apiService.getMeals();
        call.enqueue(new Callback<CategoryResponse>() {

            @Override
            public void onResponse(@NonNull Call<CategoryResponse> call, @NonNull Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: CallBack " + response.raw() + response.body().getCategories());
                    networkCallback.onSuccessResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoryResponse> call, @NonNull Throwable t) {
                Log.i(TAG, "onFailure: CallBack");
                networkCallback.onFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void searchMeal(String name, NetworkCallback networkCallback) {
        makeNetworkCall();
        Call<MealsResponse> call = apiService.searchMealsByName(name);
        call.enqueue(new Callback<MealsResponse>() {

            @Override
            public void onResponse(@NonNull Call<MealsResponse> call, @NonNull Response<MealsResponse> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: CallBack " + response.raw() + response.body().getMeals());
                    networkCallback.onSuccessResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MealsResponse> call, @NonNull Throwable t) {
                Log.i(TAG, "onFailure: CallBack");
                networkCallback.onFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void getMealOfTheDay(NetworkCallback networkCallback) {
        makeNetworkCall();
        Call<MealsResponse> call = apiService.getMealOfTheDay();
        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealsResponse> call, @NonNull Response<MealsResponse> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: CallBack " + response.raw() + response.body().getMeals());
                    networkCallback.onSuccessResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MealsResponse> call, @NonNull Throwable t) {
                Log.i(TAG, "onFailure: CallBack");
                networkCallback.onFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
