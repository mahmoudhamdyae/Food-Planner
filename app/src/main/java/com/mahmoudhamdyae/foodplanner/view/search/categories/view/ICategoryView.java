package com.mahmoudhamdyae.foodplanner.view.search.categories.view;

import com.mahmoudhamdyae.foodplanner.model.CategoryNamesResponse;

public interface ICategoryView {

    void onGetCategoriesSuccess(CategoryNamesResponse categoryResponse);

    void onGetCategoriesFail(String errorMsg);
}
