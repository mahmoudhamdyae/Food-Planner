package com.mahmoudhamdyae.foodplanner.view.fav.view;

import androidx.lifecycle.LiveData;

import com.mahmoudhamdyae.foodplanner.model.Meal;

import java.util.List;

public interface IFavView {

    void showData(LiveData<List<Meal>> meals);
}
