package com.mahmoudhamdyae.foodplanner.view.search.meals.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.db.LocalDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;
import com.mahmoudhamdyae.foodplanner.model.RepositoryImpl;
import com.mahmoudhamdyae.foodplanner.model.SearchType;
import com.mahmoudhamdyae.foodplanner.network.RemoteDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.utils.Utils;
import com.mahmoudhamdyae.foodplanner.view.search.meals.presenter.IMealsPresenter;
import com.mahmoudhamdyae.foodplanner.view.search.meals.presenter.MealsPresenter;

import java.util.ArrayList;
import java.util.Objects;

public class MealsFragment extends Fragment implements IMealsView, OnMealClickListener {

    private MealsAdapter mAdapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayout linearLayout;
    private LottieAnimationView errorImage, emptyView;
    private IMealsPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        errorImage = view.findViewById(R.id.error_image);
        emptyView = view.findViewById(R.id.empty_image);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        linearLayout = view.findViewById(R.id.linear_layout);
        RecyclerView recyclerView = view.findViewById(R.id.meals_recycler_view);

        TextView descTextView = view.findViewById(R.id.desc);

        // Get arguments from previous screen
        SearchType searchType = MealsFragmentArgs.fromBundle(getArguments()).getSearchType();
        String name = MealsFragmentArgs.fromBundle(getArguments()).getName();
        String categoryDesc = MealsFragmentArgs.fromBundle(getArguments()).getDesc();
        if (categoryDesc != null) {
            descTextView.setVisibility(View.VISIBLE);
            descTextView.setText(categoryDesc);
        } else {
            descTextView.setVisibility(View.GONE);
        }

        // Recycler View
        mAdapter = new MealsAdapter(getContext(), new ArrayList<>(), this, searchType == SearchType.PLAN);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), Utils.getNoOfColumns(requireContext()));
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        // Set Action Bar Title
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(name);

        // Presenter
        presenter = new MealsPresenter(this, RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), LocalDataSourceImpl.getInstance(requireContext())));
        presenter.getMeals(searchType, name);
    }

    @Override
    public void onGetMealsSuccess(MealsResponse mealsResponse) {
        stopShimmerEffectAndShowUi();
        mAdapter.setList(mealsResponse.getMeals());
        if (mealsResponse.getMeals().size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onGetMealsFail(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
        mShimmerViewContainer.setVisibility(View.GONE);
        errorImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMealClicked(Meal meal) {
        navigateToMealScreen(meal);
    }

    @Override
    public void onRemoveFromPlanClicked(Meal meal) {
        getString(R.string.from, meal.getArea());
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.dialog_remove_from_plan_title)
                .setMessage(getString(R.string.dialog_remove_from_plan_msg, meal.getName()))
                .setPositiveButton(R.string.dialog_remove_from_plan_yes, (dialog, id) -> {
                    // Remove the meal from Plan
                    Toast.makeText(getContext(), meal.getName() + "Removed from Plan", Toast.LENGTH_SHORT).show();
                    presenter.removeMealFromPlan(meal);
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.dialog_remove_from_plan_cancel, (dialog, id) -> {
                    // User cancelled the dialog
                    dialog.dismiss();
                }).show();
    }

    private void navigateToMealScreen(Meal meal) {
        NavDirections action = MealsFragmentDirections.actionMealsFragmentToMealFragment(meal.getId(), null);
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
        linearLayout.setVisibility(View.VISIBLE);
    }
}