package com.mahmoudhamdyae.foodplanner.view.search.names.presenter;

import com.mahmoudhamdyae.foodplanner.model.MealsResponse;
import com.mahmoudhamdyae.foodplanner.model.Repository;
import com.mahmoudhamdyae.foodplanner.network.NetworkCallback;
import com.mahmoudhamdyae.foodplanner.view.search.names.view.INamesView;

public class NamesPresenter implements INamesPresenter, NetworkCallback {

    private final INamesView view;
    private final Repository repo;

    public NamesPresenter(INamesView view, Repository repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void searchMealByName(String mealName) {
        repo.searchMeal(mealName, this);
    }

    @Override
    public void onSuccessResult(Object object) {
        view.onGetMealsSuccess((MealsResponse) object);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.onGetMealsFail(errorMsg);
    }
}
