package com.mahmoudhamdyae.foodplanner.view.search.meals.presenter;

import com.mahmoudhamdyae.foodplanner.model.MealsResponse;
import com.mahmoudhamdyae.foodplanner.model.Repository;
import com.mahmoudhamdyae.foodplanner.model.SearchType;
import com.mahmoudhamdyae.foodplanner.network.NetworkCallback;
import com.mahmoudhamdyae.foodplanner.view.search.meals.view.IMealsView;

public class MealsPresenter implements IMealsPresenter, NetworkCallback {

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
        }
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
