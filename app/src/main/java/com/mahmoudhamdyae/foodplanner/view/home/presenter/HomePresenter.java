package com.mahmoudhamdyae.foodplanner.view.home.presenter;

import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;
import com.mahmoudhamdyae.foodplanner.view.home.view.IHomeView;
import com.mahmoudhamdyae.network.ApiClient;
import com.mahmoudhamdyae.network.NetworkCallback;

import java.util.List;

public class HomePresenter implements IHomePresenter, NetworkCallback {

    private final IHomeView view;
    private final ApiClient client;

    public HomePresenter(IHomeView view, ApiClient client) {
        this.view = view;
        this.client = client;
    }

    @Override
    public List<Meal> getMeals() {
        client.makeNetworkCall(this);
        return null;
    }

    @Override
    public void onSuccessResult(MealsResponse mealsResponse) {
        view.onGetMealsSuccess(mealsResponse);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.onGetMealsFail(errorMsg);
    }
}
