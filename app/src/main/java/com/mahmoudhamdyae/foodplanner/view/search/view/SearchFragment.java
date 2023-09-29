package com.mahmoudhamdyae.foodplanner.view.search.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;
import com.mahmoudhamdyae.foodplanner.model.RepositoryImpl;
import com.mahmoudhamdyae.foodplanner.view.search.presenter.ISearchPresenter;
import com.mahmoudhamdyae.foodplanner.view.search.presenter.SearchPresenter;
import com.mahmoudhamdyae.network.ApiClient;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements ISearchView, OnMealClickListener {

    private SearchAdapter mAdapter;
    private ISearchPresenter presenter;

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

        mAdapter = new SearchAdapter(getContext(), new ArrayList<>(), this);
        RecyclerView recyclerView = view.findViewById(R.id.meals_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        presenter = new SearchPresenter(this, RepositoryImpl.getInstance(ApiClient.getInstance()));

        EditText searchEditText = view.findViewById(R.id.search_edit_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.searchMeal(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
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
        NavDirections action = SearchFragmentDirections.actionSearchFragmentToMealFragment(meal);
        Navigation.findNavController(requireView()).navigate(action);
    }
}