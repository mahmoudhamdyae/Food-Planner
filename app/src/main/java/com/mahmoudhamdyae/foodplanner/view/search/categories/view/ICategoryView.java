package com.mahmoudhamdyae.foodplanner.view.search.categories.view;

import com.mahmoudhamdyae.foodplanner.model.CategoryResponse;

public interface ICategoryView {

    void onGetCategoriesSuccess(CategoryResponse categoryResponse);

    void onGetCategoriesFail(String errorMsg);
}
