package com.mahmoudhamdyae.foodplanner.view.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.mahmoudhamdyae.foodplanner.LoadingDialog;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.SharedPref;
import com.mahmoudhamdyae.foodplanner.account.AccountServiceImpl;
import com.mahmoudhamdyae.foodplanner.account.OnResult;
import com.mahmoudhamdyae.foodplanner.db.LocalDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.model.Category;
import com.mahmoudhamdyae.foodplanner.model.CategoryResponse;
import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;
import com.mahmoudhamdyae.foodplanner.model.RepositoryImpl;
import com.mahmoudhamdyae.foodplanner.model.SearchType;
import com.mahmoudhamdyae.foodplanner.network.RemoteDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.view.home.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class HomeFragment extends Fragment implements OnCategoryClickListener, IHomeView, OnResult {

    private static final String TAG = "HomeFragment";

    private static final String MEAL_OF_THE_DAY_STATE = "MEAL_OF_THE_DAY";
    private static final String CATEGORIES_LIST_STATE = "CATEGORIES_LIST_STATE";
    private static final String IS_ERROR_STATE = "IS_ERROR_STATE";
    private HomeAdapter mAdapter;

    private ShimmerFrameLayout mShimmerViewContainer;
    private ImageView imageView;
    private TextView title, mealOfTheDayLabel, categoriesLabel;
    private CardView mealOfTheDayCardView;
    private RecyclerView recyclerView;
    private Meal mealOfTheDay;
    private ArrayList<Category> categories;
    private LottieAnimationView errorImage;

    private HomePresenter presenter;
    private SharedPref sharedPref;

    private LoadingDialog loadingDialog;

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

        // View Items
        errorImage = view.findViewById(R.id.error_image);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        imageView = view.findViewById(R.id.image_view);
        title = view.findViewById(R.id.title);
        mealOfTheDayCardView = view.findViewById(R.id.meal_of_the_day);
        mealOfTheDayLabel = view.findViewById(R.id.meal_of_the_day_label);
        categoriesLabel = view.findViewById(R.id.categories_label);
        recyclerView = view.findViewById(R.id.categories_recycler_view);
        loadingDialog = new LoadingDialog(getActivity());

        // Presenter
        presenter = new HomePresenter(this, RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), LocalDataSourceImpl.getInstance(requireContext())), new AccountServiceImpl(requireContext()));

        mealOfTheDayCardView.setOnClickListener(v -> navigateToMealScreen());
        ViewCompat.setTransitionName(imageView, "meal_image");

        sharedPref = new SharedPref(requireContext());

        if (savedInstanceState == null) {
            presenter.getMeals();
            getMealOfTheDay();
            categories = new ArrayList<>();
        } else {
            categories = savedInstanceState.getParcelableArrayList(CATEGORIES_LIST_STATE);
            mealOfTheDay = savedInstanceState.getParcelable(MEAL_OF_THE_DAY_STATE);
            setMealOfTheDayUI();
            stopShimmerEffectAndShowUi();
            if (savedInstanceState.getBoolean(IS_ERROR_STATE)) {
                recyclerView.setVisibility(View.GONE);
                errorImage.setVisibility(View.VISIBLE);
            }
        }

        // Recycler view
        mAdapter = new HomeAdapter(getContext(), categories, this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        setHasOptionsMenu(true);
    }

    private void getMealOfTheDay() {
        Map<String, Integer> savedDate = sharedPref.getDate();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        int savedDay= savedDate.get("day");
        int savedYear= savedDate.get("year");
        if (savedDay == currentDay && savedYear == currentYear) {
            // Current Day
            mealOfTheDay = sharedPref.getMeal();
            setMealOfTheDayUI();
            categoriesLabel.setVisibility(View.VISIBLE);
            mealOfTheDayLabel.setVisibility(View.VISIBLE);
            mealOfTheDayCardView.setVisibility(View.VISIBLE);
        } else {
            // New Day
            presenter.getMealOfTheDay();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
        presenter.getMeals();
        mealOfTheDay = sharedPref.getMeal();
        setMealOfTheDayUI();
    }

    @Override
    public void onPause() {
        stopShimmerEffectAndShowUi();
        super.onPause();
    }

    private void showSignOutDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setMessage(R.string.dialog_sign_out_msg)
                .setPositiveButton(R.string.dialog_sign_out, (dialog, id) -> {
                    // Sign out
                    dialog.dismiss();
                    loadingDialog.startLoadingDialog();
                    presenter.signOut();
                    navigateToAuthScreen();
                    loadingDialog.dismissDialog();
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
        int itemId = item.getItemId();
        if (itemId == R.id.log_out) {
            showSignOutDialog();
            return true;
        } else if (itemId == R.id.sign_in) {
            showSignUpDialog();
            return true;
        }
        return false;
    }

    private void showSignUpDialog() {
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

    private void navigateToMealsScreen(Category category) {
        NavDirections action = HomeFragmentDirections.actionHomeFragmentToMealsFragment(SearchType.CATEGORY, category.getName(), category.getDescription());
        Navigation.findNavController(requireView()).navigate(action);
    }

    private void navigateToMealScreen() {
        if (mealOfTheDay != null) {
            NavDirections action = HomeFragmentDirections.actionHomeFragmentToMealFragment(mealOfTheDay.getId(), mealOfTheDay);
            Navigation.findNavController(requireView()).navigate(action);
        }
    }

    @Override
    public void onCategoryClicked(Category category) {
        navigateToMealsScreen(category);
    }

    @Override
    public void onGetCategoriesSuccess(CategoryResponse categoryResponse) {
        categories = (ArrayList<Category>) categoryResponse.getCategories();
        mAdapter.setList(categories);
        stopShimmerEffectAndShowUi();
    }

    @Override
    public void onGetMealOfTheDaySuccess(MealsResponse mealsResponse) {
        mealOfTheDay = mealsResponse.getMeals().get(0);
        setMealOfTheDayUI();
        sharedPref.setMeal(mealOfTheDay);
    }

    private void setMealOfTheDayUI() {
        if (mealOfTheDay != null) {
            try {
                Glide.with(requireContext())
                        .load(mealOfTheDay.getImageUrl())
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.loading_img)
                                .error(R.drawable.ic_broken_image))
                        .into(imageView);
            } catch (IllegalStateException e) { e.printStackTrace(); }

            title.setText(mealOfTheDay.getName());
        }
    }

    @Override
    public void onNetworkFail(String errorMsg) {
        Log.d(TAG, "onNetworkFail: " + errorMsg);
        mShimmerViewContainer.setVisibility(View.GONE);
        errorImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAuthSuccess() { }

    @Override
    public void onGoogleAuthSuccess(Intent signInIntent) { }

    @Override
    public void onFailure(String errorMsg) { }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MEAL_OF_THE_DAY_STATE, mealOfTheDay);
        outState.putParcelableArrayList(CATEGORIES_LIST_STATE, categories);
        outState.putBoolean(IS_ERROR_STATE, errorImage != null && errorImage.getVisibility() == View.VISIBLE);
    }

    private void stopShimmerEffectAndShowUi() {
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
        categoriesLabel.setVisibility(View.VISIBLE);
        mealOfTheDayLabel.setVisibility(View.VISIBLE);
        mealOfTheDayCardView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}