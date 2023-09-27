package com.mahmoudhamdyae.foodplanner.view.home.view;

import android.content.DialogInterface;
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
import com.mahmoudhamdyae.foodplanner.model.Category;
import com.mahmoudhamdyae.foodplanner.model.CategoryResponse;
import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;
import com.mahmoudhamdyae.foodplanner.model.RepositoryImpl;
import com.mahmoudhamdyae.foodplanner.view.home.presenter.HomePresenter;
import com.mahmoudhamdyae.network.ApiClient;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements OnMealClickListener, IHomeView {

    private HomeAdapter mAdapter;
    private FirebaseAuth mAuth;

    private ImageView imageView;
    private TextView title, desc;

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

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mAdapter = new HomeAdapter(getContext(), new ArrayList<>(), this);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        imageView = view.findViewById(R.id.image_view);
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);

        HomePresenter presenter = new HomePresenter(this, RepositoryImpl.getInstance(ApiClient.getInstance()));
        presenter.getMeals();
        presenter.getMealOfTheDay();

        setHasOptionsMenu(true);
    }

    private void signOut() {
        new MaterialAlertDialogBuilder(requireContext())
                .setMessage(R.string.dialog_sign_out_msg)
                .setPositiveButton(R.string.dialog_sign_out, (DialogInterface.OnClickListener) (dialog, id) -> {
                    // Sign out
                    mAuth.signOut();
                    navigateToLoginScreen();
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.dialog_cancel, (DialogInterface.OnClickListener) (dialog, id) -> {
                    // User cancelled the dialog
                    dialog.dismiss();
                }).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (mAuth.getCurrentUser() != null) inflater.inflate(R.menu.menu_main, menu);
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
                navigateToLoginScreen();
                return true;
            default:
                break;
        }
        return false;
    }

    private void navigateToLoginScreen() {
        NavDirections action = HomeFragmentDirections.actionHomeFragmentToLoginFragment();
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onMealClicked(Category category) {
        Toast.makeText(getContext(), "Clicked: " + category.getStrCategory(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetMealsSuccess(CategoryResponse categoryResponse) {
        mAdapter.setList(categoryResponse.getCategories());
    }

    @Override
    public void onGetMealOfTheDaySuccess(MealsResponse mealsResponse) {
        Meal meal = mealsResponse.getMeals().get(0);
        Glide.with(requireContext())
                .load(meal.getStrMealThumb())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading_img)
                        .error(R.drawable.ic_broken_image))
                .into(imageView);

        title.setText(meal.getStrMeal());
        desc.setText(meal.getStrInstructions());
    }

    @Override
    public void onNetworkFail(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}