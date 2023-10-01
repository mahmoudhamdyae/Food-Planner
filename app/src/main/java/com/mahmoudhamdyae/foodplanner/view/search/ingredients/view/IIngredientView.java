package com.mahmoudhamdyae.foodplanner.view.search.ingredients.view;

import com.mahmoudhamdyae.foodplanner.model.IngredientResponse;

public interface IIngredientView {

    void onGetIngredientsSuccess(IngredientResponse ingredientResponse);

    void onGetIngredientsFail(String errorMsg);
}
