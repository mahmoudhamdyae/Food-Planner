package com.mahmoudhamdyae.foodplanner.view.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
import com.google.android.material.textfield.TextInputLayout;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.account.AccountService;
import com.mahmoudhamdyae.foodplanner.account.AccountServiceImpl;
import com.mahmoudhamdyae.foodplanner.account.OnResult;
import com.mahmoudhamdyae.foodplanner.utils.Validation;

public class LoginFragment extends Fragment implements OnResult {

    private static final int RC_SIGN_IN = 1;
    private TextInputLayout emailEditText, passwordEditText;

    private AccountService accountService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accountService = AccountServiceImpl.getInstance(requireContext(), this);

        // Login
        Button loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> validateAndLogin());

        // Skip - Navigate to home screen
        Button skipButton = view.findViewById(R.id.skip_button);
        skipButton.setOnClickListener(v -> skipAuth());

        // Signup - Navigate to signup screen
        TextView signupTextView = view.findViewById(R.id.signup_free);
        signupTextView.setOnClickListener(v -> {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToSignupFragment();
            Navigation.findNavController(view).navigate(action);
        });

        // Google Sign in
        SignInButton googleButton = view.findViewById(R.id.google_sign_in_button);
        googleButton.setOnClickListener(v -> accountService.signInWithGoogle());

        // TextInputs
        emailEditText = view.findViewById(R.id.email);
        passwordEditText = view.findViewById(R.id.password);
    }

    private void validateAndLogin() {
        passwordEditText.setError(null);
        emailEditText.setError(null);
        if (validateEmail() && validatePassword()) login();
    }

    private boolean validateEmail() {
        String email = emailEditText.getEditText().getText().toString();
        Validation validation = new Validation();
        if (!validation.isValidEmail(email)) {
            emailEditText.setError(getResources().getString(R.string.email_error));
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        String password = passwordEditText.getEditText().getText().toString();
        if (password.isEmpty()) {
            passwordEditText.setError(getResources().getString(R.string.empty_password_error));
            return false;
        }
        return true;
    }

    private void login() {
        String email = emailEditText.getEditText().getText().toString();
        String password = passwordEditText.getEditText().getText().toString();
        accountService.login(email, password);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                accountService.firebaseAuthWithGoogle(account.getIdToken());
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
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToHomeFragment();
        try {
            Navigation.findNavController(requireView()).navigate(action);
        } catch (IllegalStateException e) { e.printStackTrace(); }
    }

    @Override
    public void onAuthSuccess() {
        // Log in success
        navigateToHomeScreen();
    }



    @Override
    public void onGoogleAuthSuccess(Intent signInIntent) {
        try {
            startActivityForResult(signInIntent, RC_SIGN_IN);
        } catch (IllegalStateException e) { e.printStackTrace(); }
    }

    @Override
    public void onFailure(String errorMsg) {
        // Failed to log in
        Toast.makeText(getActivity(), "Authentication failed.",
                Toast.LENGTH_SHORT).show();
    }
}