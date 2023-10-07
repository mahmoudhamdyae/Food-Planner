package com.mahmoudhamdyae.foodplanner.view.search.ingredients.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class IngredientsFragment extends Fragment implements IIngredientView, OnIngredientClickListener {

    private static final String TAG = "IngredientsFragment";

    private IngredientsAdapter mAdapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView recyclerView;
    private LottieAnimationView errorImage;
    private List<Ingredient> ingredients;
    private EditText searchEditText;

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
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(R.string.ingredients_screen_title);

        // Recycler View
        ingredients = new ArrayList<>();
        mAdapter = new IngredientsAdapter(getContext(), ingredients, this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        // Presenter
        IIngredientPresenter presenter = new IngredientPresenter(this, RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), LocalDataSourceImpl.getInstance(requireContext())));
        presenter.getIngredients();

        // Search EditText
        searchEditText = view.findViewById(R.id.search_edit_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    mAdapter.setList(ingredients);
                } else {
                    mAdapter.setList(ingredients.stream().filter(ingredient -> ingredient.getName().toLowerCase().contains(s.toString().toLowerCase())).collect(Collectors.toList()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @Override
    public void onGetIngredientsSuccess(IngredientResponse ingredientResponse) {
        ingredients = ingredientResponse.getIngredients();
        stopShimmerEffectAndShowUi();
        mAdapter.setList(ingredients);
    }

    @Override
    public void onGetIngredientsFail(String errorMsg) {
        Log.d(TAG, "onGetIngredientsFail: " + errorMsg);
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
        searchEditText.setText("");
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