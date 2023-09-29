package com.mahmoudhamdyae.foodplanner.model;

import com.google.gson.annotations.SerializedName;

public class CategoryNamesResponse {

    @SerializedName("meals") private CategoryName categories;

    public CategoryNamesResponse(CategoryName categories) {
        this.categories = categories;
    }

    public CategoryName getCategories() {
        return categories;
    }

    public void setCategories(CategoryName categories) {
        this.categories = categories;
    }
}
