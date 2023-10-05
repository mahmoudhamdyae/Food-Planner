package com.mahmoudhamdyae.foodplanner.view.fav.presenter;

import com.mahmoudhamdyae.foodplanner.account.AccountService;
import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.Repository;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class FavPresenter implements IFavPresenter {

    private final Repository repo;
    private final AccountService accountService;

    public FavPresenter(Repository repo, AccountService accountService) {
        this.repo = repo;
        this.accountService = accountService;
    }

    @Override
    public Flowable<List<Meal>> observeFavMeals() {
        return repo.getFavMeals();
    }

    @Override
    public Boolean hasUser() {
        return accountService.hasUser();
    }
}
