package com.mahmoudhamdyae.foodplanner.view.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.account.AccountServiceImpl;
import com.mahmoudhamdyae.foodplanner.account.OnResult;
import com.mahmoudhamdyae.foodplanner.db.LocalDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.model.Category;
import com.mahmoudhamdyae.foodplanner.model.CategoryResponse;
import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;
import com.mahmoudhamdyae.foodplanner.model.RepositoryImpl;
import com.mahmoudhamdyae.foodplanner.view.home.presenter.HomePresenter;
import com.mahmoudhamdyae.foodplanner.network.RemoteDataSourceImpl;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements OnCategoryClickListener, IHomeView, OnResult {

    private HomeAdapter mAdapter;

    private ImageView imageView;
    private TextView title, desc;
    private Meal mealOfTheDay;

    private HomePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new HomeAdapter(getContext(), new ArrayList<>(), this);
        RecyclerView recyclerView = view.findViewById(R.id.categories_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        imageView = view.findViewById(R.id.image_view);
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);
        View row = view.findViewById(R.id.meal_of_the_day);
        row.setOnClickListener(v -> navigateToMealScreen());
        ViewCompat.setTransitionName(imageView, "meal_image");

        presenter = new HomePresenter(this, RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), LocalDataSourceImpl.getInstance(requireContext())), new AccountServiceImpl(requireContext(), this));
        presenter.getMeals();
        presenter.getMealOfTheDay();

        setHasOptionsMenu(true);
    }

    private void signOut() {
        new MaterialAlertDialogBuilder(requireContext())
                .setMessage(R.string.dialog_sign_out_msg)
                .setPositiveButton(R.string.dialog_sign_out, (dialog, id) -> {
                    // Sign out
                    presenter.signOut();
                    navigateToAuthScreen();
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.dialog_cancel, (dialog, id) -> {
                    // User cancelled the dialog
                    dialog.dismiss();
                }).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) inflater.inflate(R.menu.menu_main, menu);
        else inflater.inflate(R.menu.menu_main_not_signed, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                signOut();
                return true;
            case R.id.sign_in:
                signUp();
                return true;
            default:
                break;
        }
        return false;
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
                    dialog.dismiss();
                }).show();
    }

    private void navigateToAuthScreen() {
        NavDirections action = HomeFragmentDirections.actionHomeFragmentToAuthFragment();
        Navigation.findNavController(requireView()).navigate(action);
    }

    private void navigateToMealScreen() {
        if (mealOfTheDay != null) {
            NavDirections action = HomeFragmentDirections.actionHomeFragmentToMealFragment(mealOfTheDay);
            Navigation.findNavController(requireView()).navigate(action);
        }
    }

    @Override
    public void onCategoryClicked(Category category) {
        Toast.makeText(getContext(), "Clicked: " + category.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetMealsSuccess(CategoryResponse categoryResponse) {
        mAdapter.setList(categoryResponse.getCategories());
    }

    @Override
    public void onGetMealOfTheDaySuccess(MealsResponse mealsResponse) {
        mealOfTheDay = mealsResponse.getMeals().get(0);
        try {
            Glide.with(requireContext())
                    .load(mealOfTheDay.getImageUrl())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.loading_img)
                            .error(R.drawable.ic_broken_image))
                    .into(imageView);
        } catch (IllegalStateException e) { e.printStackTrace(); }

        title.setText(mealOfTheDay.getName());
        desc.setText(mealOfTheDay.getInstructions());
    }

    @Override
    public void onNetworkFail(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthSuccess() { }

    @Override
    public void onGoogleAuthSuccess(Intent signInIntent) { }

    @Override
    public void onFailure(String errorMsg) { }
}