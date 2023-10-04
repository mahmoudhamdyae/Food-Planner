package com.mahmoudhamdyae.foodplanner.view.meal.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
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
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.db.LocalDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.RepositoryImpl;
import com.mahmoudhamdyae.foodplanner.model.SearchType;
import com.mahmoudhamdyae.foodplanner.network.RemoteDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.view.meal.presenter.IMealPresenter;
import com.mahmoudhamdyae.foodplanner.view.meal.presenter.MealPresenter;

import java.util.ArrayList;
import java.util.List;

public class MealFragment extends Fragment implements IMealView, OnIngredientClickListener {

    private IMealPresenter presenter;
    private Boolean isFav = false;
    private MaterialButton addToCartButton;
    private TextView titleTextView, fromTextView, instTextView;
    private ImageView imageView;
    private IngredientsAdapter mAdapter;
    private WebView youtube;
    private List<Meal> favMeals;
    private Meal meal;

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

        // Button
        addToCartButton = view.findViewById(R.id.add_to_cart);
        if (!hasUser()) addToCartButton.setVisibility(View.GONE);

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
        mAdapter = new IngredientsAdapter(requireContext(), new ArrayList<>(), this);
        RecyclerView recyclerView = view.findViewById(R.id.ingredients_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        // Presenter
        presenter = new MealPresenter(this,
                RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(),
                        LocalDataSourceImpl.getInstance(requireContext())));
        // Args
        String mealId = MealFragmentArgs.fromBundle(getArguments()).getMealId();
        meal = MealFragmentArgs.fromBundle(getArguments()).getMeal();
        if (meal == null) {
            presenter.getMealById(mealId);
        } else {
            onGetMealSuccess(meal);
        }
    }

    @Override
    public void showData(List<Meal> meals) {
        favMeals = meals;
        if (favMeals != null) {
            for (int i = 0; i < favMeals.size(); i++) {
                if (favMeals.get(i).getId().equals(meal.getId())) {
                    isFav = true;
                    addToCartButton.setText(getString(R.string.remove_from_cart));
                    addToCartButton.setIcon(getResources().getDrawable(R.drawable.ic_baseline_favorite_24));
                    break;
                } else {
                    isFav = false;
                    addToCartButton.setText(getString(R.string.add_to_cart));
                    addToCartButton.setIcon(getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                }
            }
        }
    }

    @Override
    public void onGetMealSuccess(Meal meal) {
        this.meal = meal;
        presenter.getFavMeals();

        // Add to Cart Button
        addToCartButton.setOnClickListener(v -> {
            if (isFav) {
                presenter.removeMealFromFav(meal);
                Toast.makeText(requireContext(), getString(R.string.removed_toast, meal.getName()), Toast.LENGTH_SHORT).show();
                addToCartButton.setText(getString(R.string.add_to_cart));
                addToCartButton.setIcon(getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                isFav = false;
            } else {
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
        } catch (NullPointerException e) { e.printStackTrace(); }

        mAdapter.setList(ingredients);
    }

    @Override
    public void onGetMealFail(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    private Boolean hasUser() {
        // Initialize Firebase Auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null;
    }

    @Override
    public void onIngredientClicked(String ingredientName) {
        navigateToMealsFragment(ingredientName);
    }

    private void navigateToMealsFragment(String name) {
        NavDirections action = MealFragmentDirections.actionMealFragmentToMealsFragment(SearchType.INGREDIENT, name);
        Navigation.findNavController(requireView()).navigate(action);
    }
}