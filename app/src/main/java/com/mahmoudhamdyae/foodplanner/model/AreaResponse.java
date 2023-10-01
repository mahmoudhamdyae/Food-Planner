package com.mahmoudhamdyae.foodplanner.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaResponse {

    @SerializedName("meals") private List<Area> areas;

    public AreaResponse(List<Area> areas) {
        this.areas = areas;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
}
