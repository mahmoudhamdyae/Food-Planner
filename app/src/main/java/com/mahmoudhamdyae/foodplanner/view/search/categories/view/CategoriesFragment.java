package com.mahmoudhamdyae.foodplanner.view.search.categories.view;

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

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.db.LocalDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.model.Category;
import com.mahmoudhamdyae.foodplanner.model.CategoryResponse;
import com.mahmoudhamdyae.foodplanner.model.RepositoryImpl;
import com.mahmoudhamdyae.foodplanner.model.SearchType;
import com.mahmoudhamdyae.foodplanner.network.RemoteDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.view.search.categories.presenter.CategoryPresenter;
import com.mahmoudhamdyae.foodplanner.view.search.categories.presenter.ICategoryPresenter;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment implements ICategoryView, OnCategoryClickListener {

    private CategoriesAdapter mAdapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        recyclerView = view.findViewById(R.id.categories_recycler_view);

        // Set Action Bar Title
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(R.string.categories_screen_title);

        // Recycler View
        mAdapter = new CategoriesAdapter(getContext(), new ArrayList<>(), this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        // Presenter
        ICategoryPresenter presenter = new CategoryPresenter(this, RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), LocalDataSourceImpl.getInstance(requireContext())));
        presenter.getCategories();
    }

    @Override
    public void onGetCategoriesSuccess(CategoryResponse categoryResponse) {
        stopShimmerEffectAndShowUi();
        mAdapter.setList(categoryResponse.getCategories());
    }

    @Override
    public void onGetCategoriesFail(String errorMsg) {
        mShimmerViewContainer.stopShimmerAnimation();
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryClicked(Category category) {
        navigateToMealsScreen(category);
    }

    private void navigateToMealsScreen(Category category) {
        NavDirections action = CategoriesFragmentDirections.actionCategoriesFragmentToMealsFragment(SearchType.CATEGORY, category.getName(), category.getDescription());
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