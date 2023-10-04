package com.mahmoudhamdyae.foodplanner.view.search.meals.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MealsFragment extends Fragment implements IMealsView, OnMealClickListener {

    private MealsAdapter mAdapter;

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

        TextView descTextView = view.findViewById(R.id.desc);

        // Recycler View
        mAdapter = new MealsAdapter(getContext(), new ArrayList<>(), this);
        RecyclerView recyclerView = view.findViewById(R.id.meals_recycler_view);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), Utils.getNoOfColumns(requireContext()));
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

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

        // Set Action Bar Title
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(name);

        // Presenter
        IMealsPresenter presenter = new MealsPresenter(this, RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), LocalDataSourceImpl.getInstance(requireContext())));
        presenter.getMeals(searchType, name);
    }

    @Override
    public void onGetMealsSuccess(MealsResponse mealsResponse) {
        mAdapter.setList(mealsResponse.getMeals());
    }

    @Override
    public void onGetMealsFail(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMealClicked(Meal meal) {
        navigateToMealScreen(meal);
    }

    private void navigateToMealScreen(Meal meal) {
        NavDirections action = MealsFragmentDirections.actionMealsFragmentToMealFragment(meal.getId(), null);
        Navigation.findNavController(requireView()).navigate(action);
    }
}