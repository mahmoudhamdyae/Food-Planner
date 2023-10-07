package com.mahmoudhamdyae.foodplanner.view.plans;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.account.AccountService;
import com.mahmoudhamdyae.foodplanner.account.AccountServiceImpl;
import com.mahmoudhamdyae.foodplanner.model.SearchType;
import com.mahmoudhamdyae.foodplanner.utils.Utils;

public class PlansFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plans, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView saturdayCard = view.findViewById(R.id.saturday);
        CardView sundayCard = view.findViewById(R.id.sunday);
        CardView mondayCard = view.findViewById(R.id.monday);
        CardView tuesdayCard = view.findViewById(R.id.tuesday);
        CardView wednesdayCard = view.findViewById(R.id.wednesday);
        CardView thursdayCard = view.findViewById(R.id.thursday);
        CardView fridayCard = view.findViewById(R.id.friday);

        saturdayCard.setOnClickListener(v -> navigateToMealsScreen(0));
        sundayCard.setOnClickListener(v -> navigateToMealsScreen(1));
        mondayCard.setOnClickListener(v -> navigateToMealsScreen(2));
        tuesdayCard.setOnClickListener(v -> navigateToMealsScreen(3));
        wednesdayCard.setOnClickListener(v -> navigateToMealsScreen(4));
        thursdayCard.setOnClickListener(v -> navigateToMealsScreen(5));
        fridayCard.setOnClickListener(v -> navigateToMealsScreen(6));

        AccountService accountService = new AccountServiceImpl(requireContext());
        if (!accountService.hasUser()) showSignUpDialog();
    }

    private void navigateToMealsScreen(int day) {
        NavDirections action = PlansFragmentDirections.actionPlansFragmentToMealsFragment(SearchType.PLAN, getString(Utils.dayToString(day)), null);
        Navigation.findNavController(requireView()).navigate(action);
    }

    private void showSignUpDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.dialog_log_in_title)
                .setCancelable(false)
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
        NavDirections action = PlansFragmentDirections.actionPlansFragmentToAuthFragment();
        Navigation.findNavController(requireView()).navigate(action);
    }
}