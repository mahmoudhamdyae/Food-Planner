package com.mahmoudhamdyae.foodplanner.view.search.areas.view;

import com.mahmoudhamdyae.foodplanner.model.AreaResponse;

public interface IAreaView {

    void onGetAreasSuccess(AreaResponse areaResponse);

    void onGetAreasFail(String errorMsg);
}
