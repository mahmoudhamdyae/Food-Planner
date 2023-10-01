package com.mahmoudhamdyae.foodplanner.view.search.ingredients.presenter;

import com.mahmoudhamdyae.foodplanner.model.IngredientResponse;
import com.mahmoudhamdyae.foodplanner.model.Repository;
import com.mahmoudhamdyae.foodplanner.network.NetworkCallback;
import com.mahmoudhamdyae.foodplanner.view.search.ingredients.view.IIngredientView;

public class IngredientPresenter implements IIngredientPresenter, NetworkCallback {

    private final IIngredientView listener;
    private final Repository repo;

    public IngredientPresenter(IIngredientView listener, Repository repo) {
        this.listener = listener;
        this.repo = repo;
    }

    @Override
    public void getIngredients() {
        repo.getIngredients(this);
    }

    @Override
    public void onSuccessResult(Object object) {
        listener.onGetIngredientsSuccess((IngredientResponse) object);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        listener.onGetIngredientsFail(errorMsg);
    }
}
