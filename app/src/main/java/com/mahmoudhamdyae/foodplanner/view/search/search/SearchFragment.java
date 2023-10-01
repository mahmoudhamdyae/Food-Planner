package com.mahmoudhamdyae.foodplanner.view.search.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.mahmoudhamdyae.foodplanner.R;

public class SearchFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Search By Name Button
        Button searchNameButton = view.findViewById(R.id.search_by_name);
        searchNameButton.setOnClickListener(v -> navigateToNamesScreen());

        // Search By Category Button
        Button searchCategoryButton = view.findViewById(R.id.search_by_category);
        searchCategoryButton.setOnClickListener(v -> {
        });

        // Search By Area Button
        Button searchAreaButton = view.findViewById(R.id.search_by_area);
        searchAreaButton.setOnClickListener(v -> {
        });

        // Search By Ingredient Button
        Button searchIngredientButton = view.findViewById(R.id.search_by_ingredient);
        searchIngredientButton.setOnClickListener(v -> {
        });
    }

    private void navigateToNamesScreen() {
        NavDirections action = SearchFragmentDirections.actionSearchFragmentToNamesFragment();
        Navigation.findNavController(requireView()).navigate(action);
    }
}