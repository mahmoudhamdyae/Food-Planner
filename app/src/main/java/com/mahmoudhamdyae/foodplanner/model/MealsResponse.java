package com.mahmoudhamdyae.foodplanner.model;

import java.util.List;

public class MealsResponse {

    private List<Meal> categories;

    public MealsResponse(List<Meal> categories) {
        this.categories = categories;
    }

    public List<Meal> getCategories() {
        return categories;
    }

    public void setCategories(List<Meal> categories) {
        this.categories = categories;
    }
}
