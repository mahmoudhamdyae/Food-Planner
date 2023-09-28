package com.mahmoudhamdyae.foodplanner.view.meal;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.model.Meal;

public class MealFragment extends Fragment {

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

        // Button
        Button addToCartButton = view.findViewById(R.id.add_to_cart);
        addToCartButton.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Add To Cart Clicked", Toast.LENGTH_SHORT).show();
        });
    }
}