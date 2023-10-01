package com.mahmoudhamdyae.foodplanner.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryNamesResponse {

    @SerializedName("meals") private List<CategoryName> categories;

    public CategoryNamesResponse(List<CategoryName> categories) {
        this.categories = categories;
    }

    public List<CategoryName> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryName> categories) {
        this.categories = categories;
    }
}
