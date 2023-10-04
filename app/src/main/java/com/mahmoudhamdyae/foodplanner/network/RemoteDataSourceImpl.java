package com.mahmoudhamdyae.foodplanner.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mahmoudhamdyae.foodplanner.model.CategoryResponse;
import com.mahmoudhamdyae.foodplanner.model.IngredientResponse;
import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSourceImpl implements RemoteDataSource {

    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    public static final String TAG = "Remote_Data_Source";

    private static RemoteDataSourceImpl client = null;
    public static ApiService apiService;

    private static FirebaseAuth mAuth;
    private static FirebaseFirestore db;

    private RemoteDataSourceImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    private String getUserId() {
        return mAuth.getUid();
    }

    public static RemoteDataSource getInstance() {
        if (client == null) {
            client = new RemoteDataSourceImpl();
        }
        return client;
    }

    @Override
    public void getCategories(NetworkCallback networkCallback) {
        Call<CategoryResponse> call = apiService.getCategories();
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

    @Override
    public void getIngredients(NetworkCallback networkCallback) {
        Call<IngredientResponse> call = apiService.getIngredients();
        call.enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(@NonNull Call<IngredientResponse> call, @NonNull Response<IngredientResponse> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: CallBack " + response.raw() + response.body().getIngredients());
                    networkCallback.onSuccessResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<IngredientResponse> call, @NonNull Throwable t) {
                Log.i(TAG, "onFailure: CallBack");
                networkCallback.onFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void getMealsByArea(String area, NetworkCallback networkCallback) {
        Call<MealsResponse> call = apiService.getMealsByArea(area);
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
    public void getMealsByCategory(String category, NetworkCallback networkCallback) {
        Call<MealsResponse> call = apiService.getMealsByCategory(category);
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
    public void getMealsByIngredient(String ingredient, NetworkCallback networkCallback) {
        Call<MealsResponse> call = apiService.getMealsByIngredient(ingredient);
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
    public void getMealById(String mealId, NetworkCallback networkCallback) {
        Call<MealsResponse> call = apiService.getMealById(mealId);
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
    public void getFavMeals(NetworkCallback networkCallback) {
        db.collection(getUserId())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            networkCallback.onSuccessResult(document.toObject(Meal.class));
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                        networkCallback.onFailureResult(Objects.requireNonNull(task.getException()).getMessage());
                    }
                });
    }

    @Override
    public void addMealToFav(Meal meal, NetworkCallback networkCallback) {
        db.collection(getUserId()).document(meal.getId()).set(meal).addOnSuccessListener(documentReference -> {
            Log.d(TAG, "addMealToFav: " + documentReference);
        }).addOnFailureListener(e -> {
            Log.w(TAG, "Error adding document", e);
            networkCallback.onFailureResult(e.getMessage());
        });
    }

    @Override
    public void removeMealFromFav(Meal meal, NetworkCallback networkCallback) {
        db.collection(getUserId()).document(meal.getId()).delete().addOnSuccessListener(documentReference -> {
            Log.d(TAG, "removeMealToFav: " + documentReference);
        }).addOnFailureListener(e -> {
            Log.w(TAG, "Error adding document", e);
            networkCallback.onFailureResult(e.getMessage());
        });
    }
}
