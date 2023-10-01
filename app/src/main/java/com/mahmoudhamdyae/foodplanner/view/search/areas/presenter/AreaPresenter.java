package com.mahmoudhamdyae.foodplanner.view.search.areas.presenter;

import com.mahmoudhamdyae.foodplanner.model.AreaResponse;
import com.mahmoudhamdyae.foodplanner.model.Repository;
import com.mahmoudhamdyae.foodplanner.network.NetworkCallback;
import com.mahmoudhamdyae.foodplanner.view.search.areas.view.IAreaView;

public class AreaPresenter implements IAreaPresenter, NetworkCallback {

    private final IAreaView listener;
    private final Repository repo;

    public AreaPresenter(IAreaView listener, Repository repo) {
        this.listener = listener;
        this.repo = repo;
    }

    @Override
    public void getAreas() {
        repo.getAreas(this);
    }

    @Override
    public void onSuccessResult(Object object) {
        listener.onGetAreasSuccess((AreaResponse) object);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        listener.onGetAreasFail(errorMsg);
    }
}
