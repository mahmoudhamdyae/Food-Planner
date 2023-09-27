package com.mahmoudhamdyae.foodplanner.model;

import java.util.List;

public class SearchResponse {

    private List<Search> meals;

    public SearchResponse(List<Search> meals) {
        this.meals = meals;
    }

    public void setMeals(List<Search> meals) {
        this.meals = meals;
    }

    public List<Search> getMeals() {
        return meals;
    }
}
