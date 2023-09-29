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
                .load(meal.getImageUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading_img)
                        .error(R.drawable.ic_broken_image))
                .into(imageView);

        // Texts
        TextView titleTextView = view.findViewById(R.id.title);
        TextView fromTextView = view.findViewById(R.id.from);
        TextView instTextView = view.findViewById(R.id.inst);
        titleTextView.setText(meal.getName());
        fromTextView.setText(getString(R.string.from, meal.getArea()));
        instTextView.setText(meal.getInstructions());

        // Ingredients
        ingredients = new ArrayList<>();
        try {
            if (!meal.getIngredient1().equals("")) ingredients.add(meal.getIngredient1());
            if (!meal.getIngredient2().equals("")) ingredients.add(meal.getIngredient2());
            if (!meal.getIngredient3().equals("")) ingredients.add(meal.getIngredient3());
            if (!meal.getIngredient4().equals("")) ingredients.add(meal.getIngredient4());
            if (!meal.getIngredient5().equals("")) ingredients.add(meal.getIngredient5());
            if (!meal.getIngredient6().equals("")) ingredients.add(meal.getIngredient6());
            if (!meal.getIngredient7().equals("")) ingredients.add(meal.getIngredient7());
            if (!meal.getIngredient8().equals("")) ingredients.add(meal.getIngredient8());
            if (!meal.getIngredient9().equals("")) ingredients.add(meal.getIngredient9());
            if (!meal.getIngredient10().equals("")) ingredients.add(meal.getIngredient10());
            if (!meal.getIngredient11() .equals("")) ingredients.add(meal.getIngredient11());
            if (!meal.getIngredient12().equals("")) ingredients.add(meal.getIngredient12());
            if (!meal.getIngredient13().equals("")) ingredients.add(meal.getIngredient13());
            if (!meal.getIngredient14().equals("")) ingredients.add(meal.getIngredient14());
            if (!meal.getIngredient15().equals("")) ingredients.add(meal.getIngredient15());
            if (!meal.getIngredient16().equals("")) ingredients.add(meal.getIngredient16());
            if (!meal.getIngredient17().equals("")) ingredients.add(meal.getIngredient17());
            if (!meal.getIngredient18().equals("")) ingredients.add(meal.getIngredient18());
            if (!meal.getIngredient19().equals("")) ingredients.add(meal.getIngredient19());
            if (!meal.getIngredient20().equals("")) ingredients.add(meal.getIngredient20());
        } catch (NullPointerException e) { e.printStackTrace(); }

        IngredientsAdapter mAdapter = new IngredientsAdapter(requireContext(), ingredients);
        RecyclerView recyclerView = view.findViewById(R.id.ingredients_recycler_view);
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