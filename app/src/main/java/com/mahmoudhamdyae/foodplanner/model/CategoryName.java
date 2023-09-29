package com.mahmoudhamdyae.foodplanner.model;

import com.google.gson.annotations.SerializedName;

public class CategoryName {

    @SerializedName("strCategory") private String name;

    public CategoryName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String categories) {
        this.name = categories;
    }
}
