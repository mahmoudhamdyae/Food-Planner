package com.mahmoudhamdyae.foodplanner.view.meal.presenter;

import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;
import com.mahmoudhamdyae.foodplanner.model.Repository;
import com.mahmoudhamdyae.foodplanner.network.NetworkCallback;
import com.mahmoudhamdyae.foodplanner.view.meal.view.IMealView;

public class MealPresenter implements IMealPresenter, NetworkCallback {

    private final IMealView view;
    private final Repository repo;

    public MealPresenter(IMealView view, Repository repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void getMealById(String mealId) {
        repo.getMealById(mealId, this);
    }

    @Override
    public void getFavMeals() {
        repo.getFavMeals(view);
    }

    @Override
    public void addMealToFav(Meal meal) {
        repo.addMealToFav(meal);
    }

    @Override
    public void removeMealFromFav(Meal meal) {
        repo.removeMealFromFav(meal);
    }

    @Override
    public void onSuccessResult(Object object) {
        view.onGetMealSuccess((MealsResponse) object);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.onGetMealFail(errorMsg);
    }
}
