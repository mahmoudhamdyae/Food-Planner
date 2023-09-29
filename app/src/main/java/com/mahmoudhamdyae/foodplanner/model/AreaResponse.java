package com.mahmoudhamdyae.foodplanner.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaResponse {

    @SerializedName("meals") private List<Area> countries;

    public AreaResponse(List<Area> countries) {
        this.countries = countries;
    }

    public List<Area> getCountries() {
        return countries;
    }

    public void setCountries(List<Area> countries) {
        this.countries = countries;
    }
}
