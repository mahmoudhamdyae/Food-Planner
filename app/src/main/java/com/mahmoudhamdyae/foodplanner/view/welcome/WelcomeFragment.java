package com.mahmoudhamdyae.foodplanner.view.welcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.SharedPref;

public class WelcomeFragment extends Fragment {

    private static final int MAX_STEP = 3;

    private ViewPager2 viewPager;
    private Button nextButton;
    private Button skipButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.view_pager);
        nextButton = view.findViewById(R.id.next_button);
        skipButton = view.findViewById(R.id.skip_button);

        viewPager.setAdapter(new WelcomeViewPageAdapter(requireContext()));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(view.findViewById(R.id.tab_layout), viewPager, (tab, position) -> {
        });
        tabLayoutMediator.attach();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if (position == MAX_STEP - 1) {
                    nextButton.setText(R.string.welcome_get_started_button);
                    skipButton.setVisibility(View.INVISIBLE);
                } else {
                    nextButton.setText(R.string.welcome_next_button);
                    skipButton.setVisibility(View.VISIBLE);
                }
            }
        });
        viewPager.setPageTransformer(new ZoomOutPageTransformer());

        skipButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(WelcomeFragmentDirections.actionWelcomeFragmentToAuthFragment());
            changeFirstTime();
        });

        nextButton.setOnClickListener(v -> {
            if (nextButton.getText().toString().equals(getString(R.string.welcome_get_started_button))) {
                NavHostFragment.findNavController(this).navigate(WelcomeFragmentDirections.actionWelcomeFragmentToAuthFragment());
                changeFirstTime();
            } else {
                int current = viewPager.getCurrentItem() + 1;
                viewPager.setCurrentItem(current);

                // Update next button
                if (current == MAX_STEP - 1) {
                    nextButton.setText(R.string.welcome_get_started_button);
                } else {
                    nextButton.setText(R.string.welcome_next_button);
                }
            }
        });
    }

    private void changeFirstTime() {
        SharedPref sharedPref = new SharedPref(requireContext());
        sharedPref.setFirstTime();
    }
}