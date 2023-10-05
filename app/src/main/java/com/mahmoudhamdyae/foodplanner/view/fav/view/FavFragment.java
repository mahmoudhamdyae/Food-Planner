package com.mahmoudhamdyae.foodplanner.view.fav.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.account.AccountServiceImpl;
import com.mahmoudhamdyae.foodplanner.db.LocalDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.RepositoryImpl;
import com.mahmoudhamdyae.foodplanner.network.RemoteDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.utils.Utils;
import com.mahmoudhamdyae.foodplanner.view.fav.presenter.FavPresenter;
import com.mahmoudhamdyae.foodplanner.view.fav.presenter.IFavPresenter;

import java.util.ArrayList;

public class FavFragment extends Fragment implements OnMealClickListener {

    private static final String TAG = "FavFragment";

    private FavAdapter mAdapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView recyclerView;
    private LottieAnimationView emptyView, errorImage;

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

        errorImage = view.findViewById(R.id.error_image);
        emptyView = view.findViewById(R.id.empty_image);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        recyclerView = view.findViewById(R.id.fav_recycler_view);

        // Presenter
        IFavPresenter presenter = new FavPresenter(
                RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(),
                LocalDataSourceImpl.getInstance(requireContext())),
                new AccountServiceImpl(requireContext())
        );
        if (presenter.hasUser()) {
            presenter.observeFavMeals().subscribe(
                    meals -> {
                        Log.i(TAG, "onGetMeals: " + meals);
                        stopShimmerEffectAndShowUi();
                        if (meals.size() == 0) {
                            emptyView.setVisibility(View.VISIBLE);
                        } else {
                            emptyView.setVisibility(View.GONE);
                        }
                        mAdapter.setList(meals);
                        },
                    error -> {
                        errorImage.setVisibility(View.VISIBLE);
                        Log.i(TAG, "onGetMeals error: " + error.getMessage());
                        error.printStackTrace();
                    }
            );

            mAdapter = new FavAdapter(getContext(), new ArrayList<>(), this);
            recyclerView.setHasFixedSize(true);
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), Utils.getNoOfColumns(requireContext()));
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mAdapter);
        } else {
            showSignUpDialog();
        }
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

    private void navigateToMealScreen(Meal meal) {
        NavDirections action = FavFragmentDirections.actionFavFragmentToMealFragment(meal.getId(), meal);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onMealClicked(Meal meal) {
        navigateToMealScreen(meal);
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