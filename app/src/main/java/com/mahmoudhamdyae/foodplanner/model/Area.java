package com.mahmoudhamdyae.foodplanner.model;

import androidx.annotation.DrawableRes;

import com.google.gson.annotations.SerializedName;

public class Area {

    @SerializedName("strArea") private String name;
    @DrawableRes private int image;

    public Area(String name, @DrawableRes int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
