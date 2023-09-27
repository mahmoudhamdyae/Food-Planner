package com.mahmoudhamdyae.foodplanner.view.search.view;

import com.mahmoudhamdyae.foodplanner.model.SearchResponse;

public interface ISearchView {

    void onGetMealsSuccess(SearchResponse searchResponse);

    void onGetMealsFail(String errorMsg);
}
