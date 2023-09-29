package com.mahmoudhamdyae.foodplanner.view.fav.presenter;

import com.mahmoudhamdyae.foodplanner.model.Repository;
import com.mahmoudhamdyae.foodplanner.view.fav.view.IFavView;

public class FavPresenter implements IFavPresenter {

    private final Repository repo;
    private final IFavView listener;

    public FavPresenter(IFavView listener, Repository repo) {
        this.listener = listener;
        this.repo = repo;
    }

    @Override
    public void getFavMeals() {
        listener.showData(repo.observeFavMeals());
    }
}
