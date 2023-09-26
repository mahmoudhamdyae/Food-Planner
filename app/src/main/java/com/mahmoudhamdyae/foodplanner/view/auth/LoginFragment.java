package com.mahmoudhamdyae.foodplanner.view.auth;

import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.utils.Validation;

import java.util.concurrent.Executor;

public class LoginFragment extends Fragment {

    private final String TAG = "LoginFragment";
    private TextInputLayout emailEditText, passwordEditText;

    private FirebaseAuth mAuth;

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

        TextView forgotPassword = view.findViewById(R.id.forgot_password);
        forgotPassword.setOnClickListener(v -> forgotPassword());

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
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
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Navigate to Home Screen
                        NavDirections action = LoginFragmentDirections.actionLoginFragmentToHomeFragment();
                        Navigation.findNavController(getView()).navigate(action);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(getActivity(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void forgotPassword() {
    }
}