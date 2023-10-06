package com.mahmoudhamdyae.foodplanner.view.search.meals.presenter;

import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.SearchType;

public interface IMealsPresenter {

    void getMeals(SearchType searchType, String name);
    void removeMealFromPlan(Meal meal);
}
