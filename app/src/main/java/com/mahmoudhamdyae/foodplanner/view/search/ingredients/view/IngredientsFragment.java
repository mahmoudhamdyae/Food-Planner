package com.mahmoudhamdyae.foodplanner.view.search.ingredients.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.db.LocalDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.model.Ingredient;
import com.mahmoudhamdyae.foodplanner.model.IngredientResponse;
import com.mahmoudhamdyae.foodplanner.model.RepositoryImpl;
import com.mahmoudhamdyae.foodplanner.model.SearchType;
import com.mahmoudhamdyae.foodplanner.network.RemoteDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.view.search.ingredients.presenter.IIngredientPresenter;
import com.mahmoudhamdyae.foodplanner.view.search.ingredients.presenter.IngredientPresenter;

import java.util.ArrayList;

public class IngredientsFragment extends Fragment implements IIngredientView, OnIngredientClickListener {

    private IngredientsAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredients, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Recycler View
        mAdapter = new IngredientsAdapter(getContext(), new ArrayList<>(), this);
        RecyclerView recyclerView = view.findViewById(R.id.ingredients_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        // Presenter
        IIngredientPresenter presenter = new IngredientPresenter(this, RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), LocalDataSourceImpl.getInstance(requireContext())));
        presenter.getIngredients();
    }

    @Override
    public void onGetIngredientsSuccess(IngredientResponse ingredientResponse) {
        mAdapter.setList(ingredientResponse.getIngredients());
    }

    @Override
    public void onGetIngredientsFail(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onIngredientClicked(Ingredient ingredient) {
        navigateToMealsScreen(ingredient);
    }

    private void navigateToMealsScreen(Ingredient ingredient) {
        NavDirections action = IngredientsFragmentDirections.actionIngredientsFragmentToMealsFragment(SearchType.INGREDIENT, ingredient.getName());
        Navigation.findNavController(requireView()).navigate(action);
    }
}