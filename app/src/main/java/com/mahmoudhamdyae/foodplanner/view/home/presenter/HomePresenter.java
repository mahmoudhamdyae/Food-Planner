package com.mahmoudhamdyae.foodplanner.view.home.presenter;

import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;
import com.mahmoudhamdyae.foodplanner.view.home.view.IHomeView;
import com.mahmoudhamdyae.network.NetworkCallback;
import com.mahmoudhamdyae.network.RemoteSource;

import java.util.List;

public class HomePresenter implements IHomePresenter, NetworkCallback {

    private final IHomeView view;
    private final RemoteSource remoteSource;

    public HomePresenter(IHomeView view, RemoteSource remoteSource) {
        this.view = view;
        this.remoteSource = remoteSource;
    }

    @Override
    public List<Meal> getMeals() {
        remoteSource.makeNetworkCall(this);
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
