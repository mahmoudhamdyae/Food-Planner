package com.mahmoudhamdyae.foodplanner.view.search.categories.presenter;

import com.mahmoudhamdyae.foodplanner.model.CategoryNamesResponse;
import com.mahmoudhamdyae.foodplanner.model.Repository;
import com.mahmoudhamdyae.foodplanner.network.NetworkCallback;
import com.mahmoudhamdyae.foodplanner.view.search.categories.view.ICategoryView;

public class CategoryPresenter implements ICategoryPresenter, NetworkCallback {

    private final ICategoryView listener;
    private final Repository repo;

    public CategoryPresenter(ICategoryView listener, Repository repo) {
        this.listener = listener;
        this.repo = repo;
    }

    @Override
    public void getCategories() {
        repo.getCategoriesNames(this);
    }

    @Override
    public void onSuccessResult(Object object) {
        listener.onGetCategoriesSuccess((CategoryNamesResponse) object);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        listener.onGetCategoriesFail(errorMsg);
    }
}
