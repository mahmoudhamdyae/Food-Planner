package com.mahmoudhamdyae.foodplanner.view.auth.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.account.AccountServiceImpl;
import com.mahmoudhamdyae.foodplanner.view.auth.presenter.AuthPresenter;
import com.mahmoudhamdyae.foodplanner.view.auth.presenter.IAuthPresenter;

public class AuthFragment extends Fragment implements IAuthView {

    private static final int RC_SIGN_IN = 1;
    private IAuthPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new AuthPresenter(this, new AccountServiceImpl(requireContext()));

        // Skip button
        Button skipButton = view.findViewById(R.id.skip_button);
        skipButton.setOnClickListener(v -> skipAuth());

        // Log in button
        Button loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> navigateToLoginScreen());

        // Sign up button
        Button signupButton = view.findViewById(R.id.signup_button);
        signupButton.setOnClickListener(v -> navigateToSignupScreen());

        // Google Sign in
        SignInButton googleButton = view.findViewById(R.id.google_sign_in_button);
        googleButton.setOnClickListener(v -> presenter.signInWithGoogle());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                presenter.firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
            }
        }
    }

    private void skipAuth() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.dialog_skip_title)
                .setMessage(R.string.dialog_skip_msg)
                .setPositiveButton(R.string.dialog_skip_yes, (dialog, id) -> {
                    navigateToHomeScreen();
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.dialog_skip_cancel, (dialog, id) -> {
                    // User cancelled the dialog
                    dialog.dismiss();
                }).show();
    }

    private void navigateToHomeScreen() {
        NavDirections action = AuthFragmentDirections.actionAuthFragmentToHomeFragment();
        Navigation.findNavController(requireView()).navigate(action);
    }

    private void navigateToLoginScreen() {
        NavDirections action = AuthFragmentDirections.actionAuthFragmentToLoginFragment();
        Navigation.findNavController(requireView()).navigate(action);
    }

    private void navigateToSignupScreen() {
        NavDirections action = AuthFragmentDirections.actionAuthFragmentToSignupFragment();
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onAuthSuccess() {
        // Google Log in success
        navigateToHomeScreen();
    }



    @Override
    public void onGoogleAuthSuccess(Intent signInIntent) {
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onFailure(String errorMsg) {
        // Failed to log in
        Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
    }
}