package com.mahmoudhamdyae.foodplanner.view.search.presenter;

import com.mahmoudhamdyae.foodplanner.model.Repository;
import com.mahmoudhamdyae.foodplanner.model.SearchResponse;
import com.mahmoudhamdyae.foodplanner.view.search.view.ISearchView;
import com.mahmoudhamdyae.network.NetworkCallback;

public class SearchPresenter implements ISearchPresenter, NetworkCallback {

    private final ISearchView view;
    private final Repository repo;

    public SearchPresenter(ISearchView view, Repository repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void searchMeal(String mealName) {
        repo.searchMeal(mealName, this);
    }

    @Override
    public void onSuccessResult(Object object) {
        view.onGetMealsSuccess((SearchResponse) object);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.onGetMealsFail(errorMsg);
    }
}
