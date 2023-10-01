package com.mahmoudhamdyae.foodplanner;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavInflater inflater = navController.getNavInflater();
        NavGraph graph = inflater.inflate(R.navigation.nav_graph);

        // Set Start Destination (WelcomeFragment, AuthFragment or HomeFragment)
        SharedPref sharedPref = new SharedPref(this);
        if (sharedPref.isFirstTime()) {
            graph.setStartDestination(R.id.welcomeFragment);
        } else {
            if (hasUser()) {
                graph.setStartDestination(R.id.homeFragment);
            } else {
                graph.setStartDestination(R.id.authFragment);
            }
        }

        navController.setGraph(graph);

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNav, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            Log.i(TAG, "onCreate: " + destination.getDisplayName());
            int destinationId = destination.getId();

            // Screens Action Bar and Navigation Button Visibility
            if (
                    destinationId == R.id.authFragment ||
                            destinationId == R.id.loginFragment ||
                            destinationId == R.id.signupFragment ||
                            destinationId == R.id.mealFragment
            ) {
                getSupportActionBar().hide();
                findViewById(R.id.bottom_nav).setVisibility(View.GONE);
            } else {
                getSupportActionBar().show();
                findViewById(R.id.bottom_nav).setVisibility(View.VISIBLE);
            }

            // Screen Labels
            if (destinationId == R.id.searchFragment) {
                getSupportActionBar().setTitle(R.string.search_screen_title);
            } else if (destinationId == R.id.favFragment) {
                getSupportActionBar().setTitle(R.string.fav_screen_title);
            } else if (destinationId == R.id.homeFragment) {
                getSupportActionBar().setTitle(R.string.home_screen_title);
            }
        });
    }

    private Boolean hasUser() {
        // Initialize Firebase Auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null;
    }
}