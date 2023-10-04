package com.mahmoudhamdyae.foodplanner.view.fav.presenter;

import com.mahmoudhamdyae.foodplanner.account.AccountService;
import com.mahmoudhamdyae.foodplanner.model.Repository;
import com.mahmoudhamdyae.foodplanner.view.fav.view.IFavView;

public class FavPresenter implements IFavPresenter {

    private final Repository repo;
    private final IFavView listener;
    private final AccountService accountService;

    public FavPresenter(IFavView listener, Repository repo, AccountService accountService) {
        this.listener = listener;
        this.repo = repo;
        this.accountService = accountService;
    }

    @Override
    public void observeFavMeals() {
        listener.showData(repo.observeFavMeals());
    }

    @Override
    public Boolean hasUser() {
        return accountService.hasUser();
    }
}
