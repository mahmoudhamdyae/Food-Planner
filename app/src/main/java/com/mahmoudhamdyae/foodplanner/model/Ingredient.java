package com.mahmoudhamdyae.foodplanner.model;

import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("idIngredient") private String id;
    @SerializedName("strIngredient") private String name;
    @SerializedName("strDescription") private String desc;

    public Ingredient(String id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
