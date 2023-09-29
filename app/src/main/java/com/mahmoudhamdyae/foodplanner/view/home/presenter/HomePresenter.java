package com.mahmoudhamdyae.foodplanner.view.home.presenter;

import com.mahmoudhamdyae.foodplanner.model.CategoryResponse;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;
import com.mahmoudhamdyae.foodplanner.model.Repository;
import com.mahmoudhamdyae.foodplanner.view.home.view.IHomeView;
import com.mahmoudhamdyae.network.NetworkCallback;

public class HomePresenter implements IHomePresenter, NetworkCallback {

    private final IHomeView view;
    private final Repository repo;

    public HomePresenter(IHomeView view, Repository repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void getMeals() {
        repo.getCategories(this);
    }

    @Override
    public void getMealOfTheDay() {
        repo.getMealOfTheDay(this);
    }

    @Override
    public void onSuccessResult(Object object) {
        if (object instanceof CategoryResponse) {
            view.onGetMealsSuccess((CategoryResponse) object);
        } else if (object instanceof MealsResponse) {
            view.onGetMealOfTheDaySuccess((MealsResponse) object);
        }
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.onNetworkFail(errorMsg);
    }
}