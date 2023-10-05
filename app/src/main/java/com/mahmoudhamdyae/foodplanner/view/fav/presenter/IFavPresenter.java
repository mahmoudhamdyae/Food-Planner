package com.mahmoudhamdyae.foodplanner.view.fav.presenter;

import com.mahmoudhamdyae.foodplanner.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface IFavPresenter {

    Flowable<List<Meal>> observeFavMeals();
    Boolean hasUser();
}
