package com.mahmoudhamdyae.foodplanner.view.search.meals.presenter;

import android.util.Log;

import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;
import com.mahmoudhamdyae.foodplanner.model.Repository;
import com.mahmoudhamdyae.foodplanner.model.SearchType;
import com.mahmoudhamdyae.foodplanner.network.NetworkCallback;
import com.mahmoudhamdyae.foodplanner.view.search.meals.view.IMealsView;

import java.util.ArrayList;

public class MealsPresenter implements IMealsPresenter, NetworkCallback {

    private static final String TAG = "MealsPresenter";

    private final IMealsView listener;
    private final Repository repo;

    public MealsPresenter(IMealsView listener, Repository repo) {
        this.listener = listener;
        this.repo = repo;
    }

    @Override
    public void getMeals(SearchType searchType, String name) {
        switch (searchType) {
            case CATEGORY:
                repo.getMealsByCategory(name, this);
                break;
            case AREA:
                repo.getMealsByArea(name, this);
                break;
            case INGREDIENT:
                repo.getMealsByIngredient(name, this);
                break;
            case PLAN:
                repo.getFavMeals().subscribe(
                        meals -> {
                            ArrayList<Meal> arrayList = new ArrayList<>();
                            for (Meal meal: meals) {
                                if (meal.getDay() != null && meal.getDay().equals(name)) arrayList.add(meal);
                            }
                            listener.onGetMealsSuccess(new MealsResponse(arrayList));
                        },
                        error -> Log.d(TAG, "getMeals: " + error.getMessage())
                );
                break;
        }
    }

    @Override
    public void removeMealFromPlan(Meal meal) {
        repo.removeMealFromPlan(meal, this);
    }

    @Override
    public void onSuccessResult(Object object) {
        listener.onGetMealsSuccess((MealsResponse) object);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        listener.onGetMealsFail(errorMsg);
    }
}
