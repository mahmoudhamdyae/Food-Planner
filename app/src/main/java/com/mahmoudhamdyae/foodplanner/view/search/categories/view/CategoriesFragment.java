package com.mahmoudhamdyae.foodplanner.view.search.categories.view;

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
import com.mahmoudhamdyae.foodplanner.model.CategoryName;
import com.mahmoudhamdyae.foodplanner.model.CategoryNamesResponse;
import com.mahmoudhamdyae.foodplanner.model.RepositoryImpl;
import com.mahmoudhamdyae.foodplanner.model.SearchType;
import com.mahmoudhamdyae.foodplanner.network.RemoteDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.view.search.categories.presenter.CategoryPresenter;
import com.mahmoudhamdyae.foodplanner.view.search.categories.presenter.ICategoryPresenter;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment implements ICategoryView, OnCategoryClickListener {

    private CategoriesAdapter mAdapter;

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

        // Recycler View
        mAdapter = new CategoriesAdapter(getContext(), new ArrayList<>(), this);
        RecyclerView recyclerView = view.findViewById(R.id.categories_recycler_view);
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
    public void onGetCategoriesSuccess(CategoryNamesResponse categoryNamesResponse) {
        mAdapter.setList(categoryNamesResponse.getCategories());
    }

    @Override
    public void onGetCategoriesFail(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryClicked(CategoryName category) {
        navigateToMealsScreen(category);
    }

    private void navigateToMealsScreen(CategoryName category) {
        NavDirections action = CategoriesFragmentDirections.actionCategoriesFragmentToMealsFragment(SearchType.CATEGORY, category.getName());
        Navigation.findNavController(requireView()).navigate(action);
    }
}