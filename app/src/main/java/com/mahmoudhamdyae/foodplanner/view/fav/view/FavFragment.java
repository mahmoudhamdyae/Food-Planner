package com.mahmoudhamdyae.foodplanner.view.fav.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.db.LocalDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.RepositoryImpl;
import com.mahmoudhamdyae.foodplanner.network.RemoteDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.view.fav.presenter.FavPresenter;
import com.mahmoudhamdyae.foodplanner.view.fav.presenter.IFavPresenter;

import java.util.ArrayList;
import java.util.List;

public class FavFragment extends Fragment implements IFavView, OnMealClickListener {

    private FavAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (hasUser()) {
            IFavPresenter presenter = new FavPresenter(this, RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), LocalDataSourceImpl.getInstance(requireContext())));
            presenter.observeFavMeals();

            mAdapter = new FavAdapter(getContext(), new ArrayList<>(), this);
            RecyclerView recyclerView = view.findViewById(R.id.fav_recycler_view);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mAdapter);
        } else {
            signUp();
        }
    }

    private void signUp() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.dialog_log_in_title)
                .setMessage(R.string.dialog_log_in_msg)
                .setPositiveButton(R.string.dialog_log_in_yes, (dialog, id) -> {
                    navigateToAuthScreen();
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.dialog_log_in_cancel, (dialog, id) -> {
                    // User cancelled the dialog
                    navigateUp();
                    dialog.dismiss();
                }).show();
    }

    private void navigateUp() {
        Navigation.findNavController(requireView()).navigateUp();
    }

    private void navigateToAuthScreen() {
        NavDirections action = FavFragmentDirections.actionFavFragmentToAuthFragment();
        Navigation.findNavController(requireView()).navigate(action);
    }

    private Boolean hasUser() {
        // Initialize Firebase Auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null;
    }

    @Override
    public void showData(LiveData<List<Meal>> meals) {
        meals.observe(this, meals1 -> {
            mAdapter.setList(meals1);
        });
    }

    private void navigateToMealScreen(Meal meal) {
        NavDirections action = FavFragmentDirections.actionFavFragmentToMealFragment(meal.getId(), meal);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onMealClicked(Meal meal) {
        navigateToMealScreen(meal);
    }
}