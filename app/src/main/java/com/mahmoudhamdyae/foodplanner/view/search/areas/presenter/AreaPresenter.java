package com.mahmoudhamdyae.foodplanner.view.search.areas.presenter;

import com.mahmoudhamdyae.foodplanner.model.AllAreas;
import com.mahmoudhamdyae.foodplanner.model.AreaResponse;
import com.mahmoudhamdyae.foodplanner.model.Repository;
import com.mahmoudhamdyae.foodplanner.view.search.areas.view.IAreaView;

public class AreaPresenter implements IAreaPresenter {

    private final IAreaView listener;
    private final Repository repo;

    public AreaPresenter(IAreaView listener, Repository repo) {
        this.listener = listener;
        this.repo = repo;
    }

    @Override
    public void getAreas() {
        listener.onGetAreasSuccess(new AreaResponse(AllAreas.getInstance().getAllAreas()));
    }
}
