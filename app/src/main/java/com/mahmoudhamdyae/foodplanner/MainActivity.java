package com.mahmoudhamdyae.foodplanner;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavInflater inflater = navHostFragment.getNavController().getNavInflater();
        NavGraph graph = inflater.inflate(R.navigation.nav_graph);

        // Set Start Destination (WelcomeFragment, AuthFragment or HomeFragment)
        SharedPref sharedPref = new SharedPref(this);
        if (sharedPref.isFirstTime()) {
            graph.setStartDestination(R.id.welcomeFragment);
        } else {
            // Initialize Firebase Auth
            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser == null) {
                graph.setStartDestination(R.id.authFragment);
            } else {
                graph.setStartDestination(R.id.homeFragment);
            }
        }


        NavController navController = navHostFragment.getNavController();
        navController.setGraph(graph);

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNav, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.homeFragment) {
                getSupportActionBar().show();
                findViewById(R.id.bottom_nav).setVisibility(View.VISIBLE);
            } else if (destination.getId() == R.id.searchFragment) {
                getSupportActionBar().show();
                findViewById(R.id.bottom_nav).setVisibility(View.VISIBLE);
            } else if (destination.getId() == R.id.searchFragment) {
                getSupportActionBar().show();
                findViewById(R.id.bottom_nav).setVisibility(View.VISIBLE);
            }else {
                getSupportActionBar().hide();
                findViewById(R.id.bottom_nav).setVisibility(View.GONE);
            }
        });
    }
}