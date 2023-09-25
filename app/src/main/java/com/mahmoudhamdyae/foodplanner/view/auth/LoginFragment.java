package com.mahmoudhamdyae.foodplanner.view.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.utils.Validation;

public class LoginFragment extends Fragment {

    private TextInputLayout emailEditText, passwordEditText;
    private TextView forgotPassword;

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

        // Login
        Button loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> validateAndLogin());

        // Skip - Navigate to home screen
        Button skipButton = view.findViewById(R.id.skip_button);
        skipButton.setOnClickListener(v -> {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToHomeFragment();
            Navigation.findNavController(view).navigate(action);
        });

        // Signup - Navigate to signup screen
        TextView signupTextView = view.findViewById(R.id.signup_free);
        signupTextView.setOnClickListener(v -> {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToSignupFragment();
            Navigation.findNavController(view).navigate(action);
        });

        // TextInputs
        emailEditText = view.findViewById(R.id.email);
        passwordEditText = view.findViewById(R.id.password);
    }

    private void validateAndLogin() {
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
        Validation validation = new Validation();
        int error = validation.passwordErrorMessage(password);
        if (error != -1) {
            passwordEditText.setError(getResources().getString(error));
            return false;
        }
        return true;
    }

    private void login() {
    }
}