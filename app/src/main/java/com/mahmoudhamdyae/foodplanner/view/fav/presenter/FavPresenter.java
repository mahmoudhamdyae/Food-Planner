package com.mahmoudhamdyae.foodplanner.view.fav.presenter;

import com.mahmoudhamdyae.foodplanner.account.AccountService;
import com.mahmoudhamdyae.foodplanner.model.Repository;
import com.mahmoudhamdyae.foodplanner.view.fav.view.IFavView;

public class FavPresenter implements IFavPresenter {

    private final Repository repo;
    private final IFavView view;
    private final AccountService accountService;

    public FavPresenter(IFavView view, Repository repo, AccountService accountService) {
        this.view = view;
        this.repo = repo;
        this.accountService = accountService;
    }

    @Override
    public void observeFavMeals() {
        view.showData(repo.observeFavMeals());
    }

    @Override
    public Boolean hasUser() {
        return accountService.hasUser();
    }
}
