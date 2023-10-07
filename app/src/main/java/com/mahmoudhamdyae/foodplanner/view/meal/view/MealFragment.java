package com.mahmoudhamdyae.foodplanner.view.meal.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.account.AccountServiceImpl;
import com.mahmoudhamdyae.foodplanner.db.LocalDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.RepositoryImpl;
import com.mahmoudhamdyae.foodplanner.model.SearchType;
import com.mahmoudhamdyae.foodplanner.network.RemoteDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.utils.Utils;
import com.mahmoudhamdyae.foodplanner.view.meal.presenter.IMealPresenter;
import com.mahmoudhamdyae.foodplanner.view.meal.presenter.MealPresenter;

import java.util.ArrayList;
import java.util.List;

public class MealFragment extends Fragment implements IMealView, OnIngredientClickListener {

    private static final String TAG = "MealFragment";

    private IMealPresenter presenter;
    private Boolean isFav = false;
    private MaterialButton addToCartButton;
    private TextView titleTextView, fromTextView, instTextView;
    private ImageView imageView;
    private IngredientsAdapter mAdapter;
    private WebView youtube;
    private Meal meal;
    private ShimmerFrameLayout mShimmerViewContainer;
    private ScrollView scrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ConstraintLayout bottomLayout = view.findViewById(R.id.bottom_layout);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        scrollView = view.findViewById(R.id.scroll_view);

        // Presenter
        presenter = new MealPresenter(this,
                RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(),
                        LocalDataSourceImpl.getInstance(requireContext())),
                new AccountServiceImpl(requireContext()));

        // Add to plan Button
        MaterialButton addToPlan = view.findViewById(R.id.add_to_plan);
        addToPlan.setOnClickListener(v -> addToPlan());

        // Add to calender Button
        ImageButton addToCalender = view.findViewById(R.id.add_to_calender);
        addToCalender.setOnClickListener(v -> showDatePicker());

        // Add to cart Button
        addToCartButton = view.findViewById(R.id.add_to_cart);
        if (!presenter.hasUser()) bottomLayout.setVisibility(View.GONE);

        // Image
        imageView = view.findViewById(R.id.image_view);
        ViewCompat.setTransitionName(imageView, "meal_image"); // Transition

        // Texts
        titleTextView = view.findViewById(R.id.title);
        fromTextView = view.findViewById(R.id.from);
        instTextView = view.findViewById(R.id.inst);

        // YouTube Player
        youtube = view.findViewById(R.id.youtube);

        // Recycler View
        mAdapter = new IngredientsAdapter(requireContext(), new ArrayList<>(), new ArrayList<>(), this);
        RecyclerView recyclerView = view.findViewById(R.id.ingredients_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        // Args
        String mealId = MealFragmentArgs.fromBundle(getArguments()).getMealId();
        meal = MealFragmentArgs.fromBundle(getArguments()).getMeal();
        if (meal == null) {
            presenter.getMealById(mealId);
        } else {
            onGetMealSuccess(meal);
        }
    }

    private void addToPlan() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.add_to_plan_dialog_title)
                .setItems(R.array.days_array, (dialog, which) -> {
                    if (meal.getFavourite() == null || !meal.getFavourite()) {
                        meal.setFavourite(false);
                    } else {
                        meal.setFavourite(true);
                    }
                    meal.setDay(getString(Utils.dayToString(which)));
                    presenter.addToPlan(meal);
                    Toast.makeText(getContext(), meal.getName() + " Added to Plan", Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    private void showDatePicker() {
        DialogFragment newFragment = new DatePickerFragment(meal.getName());
        newFragment.show(requireActivity().getSupportFragmentManager(), "datePicker");
    }

    private void determineFavMealOrNot(List<Meal> meals) {
        try {
            if (meals != null) {
                for (int i = 0; i < meals.size(); i++) {
                    if (meals.get(i).getId().equals(meal.getId())) {
                        if (meals.get(i).getFavourite() != null) meal.setFavourite(meals.get(i).getFavourite());
                        if (meals.get(i).getDay() != null) meal.setDay(meals.get(i).getDay());
                        if (meals.get(i).getFavourite()) {
                            isFav = true;
                            addToCartButton.setText(getString(R.string.remove_from_cart));
                            addToCartButton.setIcon(getResources().getDrawable(R.drawable.ic_baseline_favorite_24));
                        } else {
                            isFav = false;
                            addToCartButton.setText(getString(R.string.add_to_cart));
                            addToCartButton.setIcon(getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                        }
                        break;
                    } else {
                        meal.setFavourite(false);
                        meal.setDay("");
                        isFav = false;
                        addToCartButton.setText(getString(R.string.add_to_cart));
                        addToCartButton.setIcon(getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                    }
                }
            }
        } catch (IllegalStateException e) { e.printStackTrace(); }
    }

    @Override
    public void onGetMealSuccess(Meal meal) {
        stopShimmerEffectAndShowUi();

        this.meal = meal;
        presenter.getFavMeals().subscribe(
                meals -> {
                    Log.i(TAG, "onGetMeals: " + meals);
                    determineFavMealOrNot(meals);
                },
                error -> {
                    mShimmerViewContainer.stopShimmerAnimation();
//                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onGetMeals error: " + error.getMessage());
                    error.printStackTrace();
                }
        );

        try {
            // Add to Cart Button
            addToCartButton.setOnClickListener(v -> {
                if (isFav) {
                    presenter.removeMealFromFav(meal);
                    Toast.makeText(requireContext(), getString(R.string.removed_toast, meal.getName()), Toast.LENGTH_SHORT).show();
                    addToCartButton.setText(getString(R.string.add_to_cart));
                    addToCartButton.setIcon(getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                    isFav = false;
                } else {
                    meal.setFavourite(true);
                    presenter.addMealToFav(meal);
                    Toast.makeText(requireContext(), getString(R.string.added_toast, meal.getName()), Toast.LENGTH_SHORT).show();
                    addToCartButton.setText(getString(R.string.remove_from_cart));
                    addToCartButton.setIcon(getResources().getDrawable(R.drawable.ic_baseline_favorite_24));
                    isFav = true;
                }
            });

            // Meal Image
            Glide.with(requireContext())
                    .load(meal.getImageUrl())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.loading_img)
                            .error(R.drawable.ic_broken_image))
                    .into(imageView);
            // Meal Text
            titleTextView.setText(meal.getName());
            fromTextView.setText(getString(R.string.from, meal.getArea()));
            instTextView.setText(meal.getInstructions());

            // YouTube Player
            String videoUrl = meal.getYoutubeUrl().replace("watch?v=", "embed/");
            String video = "<iframe width=\"100%\" height=\"100%\" src=\"" + videoUrl + "\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
            youtube.loadData(video, "text/html","utf-8");
            youtube.getSettings().setJavaScriptEnabled(true);
            youtube.setWebChromeClient(new WebChromeClient());

            // Ingredients
            ArrayList<String> ingredients = new ArrayList<>();
            ArrayList<String> measures = new ArrayList<>();
            try {
                if (!meal.getIngredient1().equals("") && meal.getIngredient1() != null) ingredients.add(meal.getIngredient1());
                if (!meal.getIngredient2().equals("") && meal.getIngredient2() != null) ingredients.add(meal.getIngredient2());
                if (!meal.getIngredient3().equals("") && meal.getIngredient3() != null) ingredients.add(meal.getIngredient3());
                if (!meal.getIngredient4().equals("") && meal.getIngredient4() != null) ingredients.add(meal.getIngredient4());
                if (!meal.getIngredient5().equals("") && meal.getIngredient5() != null) ingredients.add(meal.getIngredient5());
                if (!meal.getIngredient6().equals("") && meal.getIngredient6() != null) ingredients.add(meal.getIngredient6());
                if (!meal.getIngredient7().equals("") && meal.getIngredient7() != null) ingredients.add(meal.getIngredient7());
                if (!meal.getIngredient8().equals("") && meal.getIngredient8() != null) ingredients.add(meal.getIngredient8());
                if (!meal.getIngredient9().equals("") && meal.getIngredient9() != null) ingredients.add(meal.getIngredient9());
                if (!meal.getIngredient10().equals("") && meal.getIngredient10() != null) ingredients.add(meal.getIngredient10());
                if (!meal.getIngredient11() .equals("") && meal.getIngredient11() != null) ingredients.add(meal.getIngredient11());
                if (!meal.getIngredient12().equals("") && meal.getIngredient12() != null) ingredients.add(meal.getIngredient12());
                if (!meal.getIngredient13().equals("") && meal.getIngredient13() != null) ingredients.add(meal.getIngredient13());
                if (!meal.getIngredient14().equals("") && meal.getIngredient14() != null) ingredients.add(meal.getIngredient14());
                if (!meal.getIngredient15().equals("") && meal.getIngredient15() != null) ingredients.add(meal.getIngredient15());
                if (!meal.getIngredient16().equals("") && meal.getIngredient16() != null) ingredients.add(meal.getIngredient16());
                if (!meal.getIngredient17().equals("") && meal.getIngredient17() != null) ingredients.add(meal.getIngredient17());
                if (!meal.getIngredient18().equals("") && meal.getIngredient18() != null) ingredients.add(meal.getIngredient18());
                if (!meal.getIngredient19().equals("") && meal.getIngredient19() != null) ingredients.add(meal.getIngredient19());
                if (!meal.getIngredient20().equals("") && meal.getIngredient20() != null) ingredients.add(meal.getIngredient20());

                if (!meal.getMeasure1().equals("") && meal.getMeasure1() != null) measures.add(meal.getMeasure1());
                if (!meal.getMeasure2().equals("") && meal.getMeasure2() != null) measures.add(meal.getMeasure2());
                if (!meal.getMeasure3().equals("") && meal.getMeasure3() != null) measures.add(meal.getMeasure3());
                if (!meal.getMeasure4().equals("") && meal.getMeasure4() != null) measures.add(meal.getMeasure4());
                if (!meal.getMeasure5().equals("") && meal.getMeasure5() != null) measures.add(meal.getMeasure5());
                if (!meal.getMeasure6().equals("") && meal.getMeasure6() != null) measures.add(meal.getMeasure6());
                if (!meal.getMeasure7().equals("") && meal.getMeasure7() != null) measures.add(meal.getMeasure7());
                if (!meal.getMeasure8().equals("") && meal.getMeasure8() != null) measures.add(meal.getMeasure8());
                if (!meal.getMeasure9().equals("") && meal.getMeasure9() != null) measures.add(meal.getMeasure9());
                if (!meal.getMeasure10().equals("") && meal.getMeasure10() != null) measures.add(meal.getMeasure10());
                if (!meal.getMeasure11().equals("") && meal.getMeasure11() != null) measures.add(meal.getMeasure11());
                if (!meal.getMeasure12().equals("") && meal.getMeasure12() != null) measures.add(meal.getMeasure12());
                if (!meal.getMeasure13().equals("") && meal.getMeasure13() != null) measures.add(meal.getMeasure13());
                if (!meal.getMeasure14().equals("") && meal.getMeasure14() != null) measures.add(meal.getMeasure14());
                if (!meal.getMeasure15().equals("") && meal.getMeasure15() != null) measures.add(meal.getMeasure15());
                if (!meal.getMeasure16().equals("") && meal.getMeasure16() != null) measures.add(meal.getMeasure16());
                if (!meal.getMeasure17().equals("") && meal.getMeasure17() != null) measures.add(meal.getMeasure17());
                if (!meal.getMeasure18().equals("") && meal.getMeasure18() != null) measures.add(meal.getMeasure18());
                if (!meal.getMeasure19().equals("") && meal.getMeasure19() != null) measures.add(meal.getMeasure19());
                if (!meal.getMeasure20().equals("") && meal.getMeasure20() != null) measures.add(meal.getMeasure20());
            } catch (NullPointerException e) { e.printStackTrace(); }

            mAdapter.setList(ingredients, measures);
        } catch (IllegalStateException e) { e.printStackTrace(); }
    }

    @Override
    public void onGetMealFail(String errorMsg) {
        mShimmerViewContainer.stopShimmerAnimation();
        Log.d(TAG, "onGetMealFail: " + errorMsg);
    }

    @Override
    public void onIngredientClicked(String ingredientName) {
        navigateToMealsFragment(ingredientName);
    }

    private void navigateToMealsFragment(String name) {
        NavDirections action = MealFragmentDirections.actionMealFragmentToMealsFragment(SearchType.INGREDIENT, name, null);
        Navigation.findNavController(requireView()).navigate(action);
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
        scrollView.setVisibility(View.VISIBLE);
    }
}