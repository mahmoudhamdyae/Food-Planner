package com.mahmoudhamdyae.foodplanner.model;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("idCategory") private String id;
    @SerializedName("strCategory") private String name;
    @SerializedName("strCategoryDescription") private String description;
    @SerializedName("strCategoryThumb") private String imageUrl;

    public Category(String id, String name, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
