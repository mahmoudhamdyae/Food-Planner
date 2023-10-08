package com.mahmoudhamdyae.foodplanner.view.search.names.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.db.LocalDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;
import com.mahmoudhamdyae.foodplanner.model.RepositoryImpl;
import com.mahmoudhamdyae.foodplanner.network.RemoteDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.utils.Utils;
import com.mahmoudhamdyae.foodplanner.view.search.names.presenter.INamesPresenter;
import com.mahmoudhamdyae.foodplanner.view.search.names.presenter.NamesPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NamesFragment extends Fragment implements INamesView, OnMealClickListener {

    private static final String TAG = "NamesFragment";

    private NamesAdapter mAdapter;
    private INamesPresenter presenter;
    private LottieAnimationView emptyView, searchImage, errorImage;
    private RecyclerView recyclerView;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_names, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emptyView = view.findViewById(R.id.empty_image);
        searchImage = view.findViewById(R.id.search_image);
        recyclerView = view.findViewById(R.id.meals_recycler_view);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        errorImage = view.findViewById(R.id.error_image);
        EditText searchEditText = view.findViewById(R.id.search_edit_text);

        // Set Action Bar Title
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(R.string.names_screen_title);

        // Recycler View
        mAdapter = new NamesAdapter(getContext(), new ArrayList<>(), this);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), Utils.getNoOfColumns(requireContext()));
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        // Presenter
        presenter = new NamesPresenter(this, RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), LocalDataSourceImpl.getInstance(requireContext())));

        // Search EditText
        Observable<String> observable = Observable.create(emitter -> searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                mShimmerViewContainer.setVisibility(View.VISIBLE);
                mShimmerViewContainer.startShimmerAnimation();
                searchImage.setVisibility(View.GONE);
                emitter.onNext(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        }));
        observable.doOnNext(searchString -> {
            Log.i(TAG, "upStream: " + searchString);
        })
                .subscribeOn(Schedulers.computation())
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchString -> {
                    Log.i(TAG, "downStream: " + searchString);
                    presenter.searchMealByName(searchString);
                }, error -> Log.d(TAG, "onViewCreated error: " + error.getMessage()));
    }

    @Override
    public void onGetMealsSuccess(MealsResponse mealsResponse) {
        List<Meal> meals = mealsResponse.getMeals();
        if (meals == null || meals.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
        errorImage.setVisibility(View.GONE);
        stopShimmerEffectAndShowUi();
        mAdapter.setList(meals);
    }

    @Override
    public void onGetMealsFail(String errorMsg) {
        Log.d(TAG, "onGetMealsFail: " + errorMsg);
        mShimmerViewContainer.setVisibility(View.GONE);
        errorImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMealClicked(Meal meal) {
        navigateToMealScreen(meal);
    }

    private void navigateToMealScreen(Meal meal) {
        NavDirections action = NamesFragmentDirections.actionNamesFragmentToMealFragment(meal.getId(), null);
        Navigation.findNavController(requireView()).navigate(action);
    }

    private void stopShimmerEffectAndShowUi() {
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}