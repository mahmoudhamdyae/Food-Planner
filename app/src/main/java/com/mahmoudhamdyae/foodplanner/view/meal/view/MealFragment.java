package com.mahmoudhamdyae.foodplanner.view.meal.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.model.Meal;

import java.util.ArrayList;

public class MealFragment extends Fragment {

    private ArrayList<String> ingredients;

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

        Meal meal = MealFragmentArgs.fromBundle(getArguments()).getMeal();

        // Image
        ImageView imageView = view.findViewById(R.id.image_view);
        ViewCompat.setTransitionName(imageView, "meal_image"); // Transition
        Glide.with(requireContext())
                .load(meal.getStrMealThumb())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading_img)
                        .error(R.drawable.ic_broken_image))
                .into(imageView);

        // Texts
        TextView titleTextView = view.findViewById(R.id.title);
        TextView fromTextView = view.findViewById(R.id.from);
        TextView instTextView = view.findViewById(R.id.inst);
        titleTextView.setText(meal.getStrMeal());
        fromTextView.setText(meal.getStrArea());
        instTextView.setText(meal.getStrInstructions());

        // Ingredients
        ingredients = new ArrayList<>();
        if (meal.getStrIngredient1() != null) ingredients.add(meal.getStrIngredient1());
        if (meal.getStrIngredient2() != null) ingredients.add(meal.getStrIngredient2());
        if (meal.getStrIngredient3() != null) ingredients.add(meal.getStrIngredient3());
        if (meal.getStrIngredient4() != null) ingredients.add(meal.getStrIngredient4());
        if (meal.getStrIngredient5() != null) ingredients.add(meal.getStrIngredient5());
        if (meal.getStrIngredient6() != null) ingredients.add(meal.getStrIngredient6());
        if (meal.getStrIngredient7() != null) ingredients.add(meal.getStrIngredient7());
        if (meal.getStrIngredient8() != null) ingredients.add(meal.getStrIngredient8());
        if (meal.getStrIngredient9() != null) ingredients.add(meal.getStrIngredient9());
        if (meal.getStrIngredient10() != null) ingredients.add(meal.getStrIngredient10());
        if (meal.getStrIngredient11() != null) ingredients.add(meal.getStrIngredient11());
        if (meal.getStrIngredient12() != null) ingredients.add(meal.getStrIngredient12());
        if (meal.getStrIngredient13() != null) ingredients.add(meal.getStrIngredient13());
        if (meal.getStrIngredient14() != null) ingredients.add(meal.getStrIngredient14());
        if (meal.getStrIngredient15() != null) ingredients.add(meal.getStrIngredient15());
        if (meal.getStrIngredient16() != null) ingredients.add(meal.getStrIngredient16());
        if (meal.getStrIngredient17() != null) ingredients.add(meal.getStrIngredient17());
        if (meal.getStrIngredient18() != null) ingredients.add(meal.getStrIngredient18());
        if (meal.getStrIngredient19() != null) ingredients.add(meal.getStrIngredient19());
        if (meal.getStrIngredient20() != null) ingredients.add(meal.getStrIngredient20());

        IngredientsAdapter mAdapter = new IngredientsAdapter(requireContext(), ingredients);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        // Button
        Button addToCartButton = view.findViewById(R.id.add_to_cart);
        addToCartButton.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Add To Cart Clicked", Toast.LENGTH_SHORT).show();
        });
    }
}