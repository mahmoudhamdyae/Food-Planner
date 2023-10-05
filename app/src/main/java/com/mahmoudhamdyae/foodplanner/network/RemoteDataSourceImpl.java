package com.mahmoudhamdyae.foodplanner.network;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mahmoudhamdyae.foodplanner.model.CategoryResponse;
import com.mahmoudhamdyae.foodplanner.model.IngredientResponse;
import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;

import java.util.Objects;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSourceImpl implements RemoteDataSource {

    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    public static final String TAG = "Remote_Data_Source";

    private static RemoteDataSourceImpl client = null;
    public static ApiService apiService;

    private static FirebaseAuth mAuth;
    private final FirebaseFirestore db;

    private RemoteDataSourceImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
        Flowable<CategoryResponse> flowable = apiService.getCategories();
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categoryResponse -> {
                            Log.i(TAG, "onResponse: CallBack " + categoryResponse.getCategories());
                            networkCallback.onSuccessResult(categoryResponse);
                        },
                        error -> {
                            networkCallback.onFailureResult(error.getMessage());
                            Log.i(TAG, "onFailure: CallBack");
                            error.printStackTrace();
                        }
                );
    }

    @Override
    public void searchMeal(String name, NetworkCallback networkCallback) {
        Flowable<MealsResponse> flowable = apiService.searchMealsByName(name);
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealsResponse -> {
                            Log.i(TAG, "onResponse: CallBack " + mealsResponse.getMeals());
                            networkCallback.onSuccessResult(mealsResponse);
                        },
                        error -> {
                            networkCallback.onFailureResult(error.getMessage());
                            Log.i(TAG, "onFailure: CallBack");
                            error.printStackTrace();
                        }
                );
    }

    @Override
    public void getMealOfTheDay(NetworkCallback networkCallback) {
        Flowable<MealsResponse> flowable = apiService.getMealOfTheDay();
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealsResponse -> {
                            Log.i(TAG, "onResponse: CallBack " + mealsResponse.getMeals());
                            networkCallback.onSuccessResult(mealsResponse);
                        },
                        error -> {
                            networkCallback.onFailureResult(error.getMessage());
                            Log.i(TAG, "onFailure: CallBack");
                            error.printStackTrace();
                        }
                );
    }

    @Override
    public void getIngredients(NetworkCallback networkCallback) {
        Flowable<IngredientResponse> flowable = apiService.getIngredients();
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ingredientResponse -> {
                            Log.i(TAG, "onResponse: CallBack " + ingredientResponse.getIngredients());
                            networkCallback.onSuccessResult(ingredientResponse);
                        },
                        error -> {
                            networkCallback.onFailureResult(error.getMessage());
                            Log.i(TAG, "onFailure: CallBack");
                            error.printStackTrace();
                        }
                );
    }

    @Override
    public void getMealsByArea(String area, NetworkCallback networkCallback) {
        Flowable<MealsResponse> flowable = apiService.getMealsByArea(area);
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealsResponse -> {
                            Log.i(TAG, "onResponse: CallBack " + mealsResponse.getMeals());
                            networkCallback.onSuccessResult(mealsResponse);
                        },
                        error -> {
                            networkCallback.onFailureResult(error.getMessage());
                            Log.i(TAG, "onFailure: CallBack");
                            error.printStackTrace();
                        }
                );
    }

    @Override
    public void getMealsByCategory(String category, NetworkCallback networkCallback) {
        Flowable<MealsResponse> flowable = apiService.getMealsByCategory(category);
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealsResponse -> {
                            Log.i(TAG, "onResponse: CallBack " + mealsResponse.getMeals());
                            networkCallback.onSuccessResult(mealsResponse);
                        },
                        error -> {
                            networkCallback.onFailureResult(error.getMessage());
                            Log.i(TAG, "onFailure: CallBack");
                            error.printStackTrace();
                        }
                );
    }

    @Override
    public void getMealsByIngredient(String ingredient, NetworkCallback networkCallback) {
        Flowable<MealsResponse> flowable = apiService.getMealsByIngredient(ingredient);
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealsResponse -> {
                            Log.i(TAG, "onResponse: CallBack " + mealsResponse.getMeals());
                            networkCallback.onSuccessResult(mealsResponse);
                        },
                        error -> {
                            networkCallback.onFailureResult(error.getMessage());
                            Log.i(TAG, "onFailure: CallBack");
                            error.printStackTrace();
                        }
                );
    }

    @Override
    public void getMealById(String mealId, NetworkCallback networkCallback) {
        Flowable<MealsResponse> flowable = apiService.getMealById(mealId);
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealsResponse -> {
                            Log.i(TAG, "onResponse: CallBack " + mealsResponse.getMeals());
                            networkCallback.onSuccessResult(mealsResponse);
                        },
                        error -> {
                            networkCallback.onFailureResult(error.getMessage());
                            Log.i(TAG, "onFailure: CallBack");
                            error.printStackTrace();
                        }
                );
    }

    // Firebase Firestore

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
