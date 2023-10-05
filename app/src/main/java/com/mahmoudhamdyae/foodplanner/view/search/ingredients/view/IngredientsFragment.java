package com.mahmoudhamdyae.foodplanner.view.search.ingredients.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;
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
    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView recyclerView;
    private LottieAnimationView errorImage;

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

        errorImage = view.findViewById(R.id.error_image);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        recyclerView = view.findViewById(R.id.ingredients_recycler_view);

        // Set Action Bar Title
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(R.string.ingredients_screen_title);

        // Recycler View
        mAdapter = new IngredientsAdapter(getContext(), new ArrayList<>(), this);
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
        stopShimmerEffectAndShowUi();
        mAdapter.setList(ingredientResponse.getIngredients());
    }

    @Override
    public void onGetIngredientsFail(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
        mShimmerViewContainer.setVisibility(View.GONE);
        errorImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onIngredientClicked(Ingredient ingredient) {
        navigateToMealsScreen(ingredient);
    }

    private void navigateToMealsScreen(Ingredient ingredient) {
        NavDirections action = IngredientsFragmentDirections.actionIngredientsFragmentToMealsFragment(SearchType.INGREDIENT, ingredient.getName(), null);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        stopShimmerEffectAndShowUi();
        super.onPause();
    }

    private void stopShimmerEffectAndShowUi() {
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}