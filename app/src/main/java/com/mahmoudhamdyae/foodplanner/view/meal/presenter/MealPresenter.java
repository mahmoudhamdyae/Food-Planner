package com.mahmoudhamdyae.foodplanner.view.meal.presenter;

import com.mahmoudhamdyae.foodplanner.account.AccountService;
import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;
import com.mahmoudhamdyae.foodplanner.model.Repository;
import com.mahmoudhamdyae.foodplanner.network.NetworkCallback;
import com.mahmoudhamdyae.foodplanner.view.meal.view.IMealView;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class MealPresenter implements IMealPresenter, NetworkCallback {

    private final IMealView view;
    private final Repository repo;
    private final AccountService accountService;

    public MealPresenter(IMealView view, Repository repo, AccountService accountService) {
        this.view = view;
        this.repo = repo;
        this.accountService = accountService;
    }

    @Override
    public void getMealById(String mealId) {
        repo.getMealById(mealId, this);
    }

    @Override
    public Flowable<List<Meal>> getFavMeals() {
        return repo.getFavMeals();
    }

    @Override
    public void addMealToFav(Meal meal) {
        repo.addMealToFav(meal, this);
    }

    @Override
    public void removeMealFromFav(Meal meal) {
        repo.removeMealFromFav(meal, this);
    }

    @Override
    public Boolean hasUser() {
        return accountService.hasUser();
    }

    @Override
    public void onSuccessResult(Object object) {
        if (object instanceof  MealsResponse) {
            view.onGetMealSuccess(((MealsResponse) object).getMeals().get(0));
        }
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.onGetMealFail(errorMsg);
    }
}
