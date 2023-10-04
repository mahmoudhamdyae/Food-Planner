package com.mahmoudhamdyae.foodplanner.view.home.presenter;

import com.mahmoudhamdyae.foodplanner.account.AccountService;
import com.mahmoudhamdyae.foodplanner.model.CategoryResponse;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;
import com.mahmoudhamdyae.foodplanner.model.Repository;
import com.mahmoudhamdyae.foodplanner.view.home.view.IHomeView;
import com.mahmoudhamdyae.foodplanner.network.NetworkCallback;

public class HomePresenter implements IHomePresenter, NetworkCallback {

    private final IHomeView view;
    private final Repository repo;
    private final AccountService accountService;

    public HomePresenter(IHomeView view, Repository repo, AccountService accountService) {
        this.view = view;
        this.repo = repo;
        this.accountService = accountService;
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
    public void signOut() {
        accountService.signOut();
    }

    @Override
    public void onSuccessResult(Object object) {
        if (object instanceof CategoryResponse) {
            view.onGetCategoriesSuccess((CategoryResponse) object);
        } else if (object instanceof MealsResponse) {
            view.onGetMealOfTheDaySuccess((MealsResponse) object);
        }
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.onNetworkFail(errorMsg);
    }
}
