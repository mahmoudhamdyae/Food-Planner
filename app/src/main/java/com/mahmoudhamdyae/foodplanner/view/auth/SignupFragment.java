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

public class SignupFragment extends Fragment {

    private final String TAG = "SignupFragment";
    private TextInputLayout userNameEditText, emailEditText, passwordEditText, repeatPasswordEditText;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Login - Navigate Back
        TextView loginButton = view.findViewById(R.id.login);
        loginButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigateUp();
        });

        // Skip - Navigate to home screen
        Button skipButton = view.findViewById(R.id.skip_button);
        skipButton.setOnClickListener(v -> {
            NavDirections action = SignupFragmentDirections.actionSignupFragmentToHomeFragment();
            Navigation.findNavController(view).navigate(action);
        });

        // Signup - Navigate to signup screen
        Button signupTextView = view.findViewById(R.id.signup_button);
        signupTextView.setOnClickListener(v -> {
            validateAndSignup();
        });

        // TextInputs
        userNameEditText = view.findViewById(R.id.user_name);
        emailEditText = view.findViewById(R.id.email);
        passwordEditText = view.findViewById(R.id.password);
        repeatPasswordEditText = view.findViewById(R.id.repeat_password);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    private void validateAndSignup() {
        passwordEditText.setError(null);
        emailEditText.setError(null);
        repeatPasswordEditText.setError(null);
        userNameEditText.setError(null);
        if (validateUserName() && validateEmail() && validatePassword() && validateRepeatPassword()) {
            signup();
        }
    }

    private boolean validateUserName() {
        String userName = userNameEditText.getEditText().getText().toString();
        Validation validation = new Validation();
        if (!validation.isValidUserName(userName)) {
            userNameEditText.setError(getResources().getString(R.string.user_name_error));
            return false;
        }
        return true;
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

    private boolean validateRepeatPassword() {
        String password = passwordEditText.getEditText().getText().toString();
        String repeatedPassword = repeatPasswordEditText.getEditText().getText().toString();
        Validation validation = new Validation();
        if (!validation.passwordMatches(password, repeatedPassword)) {
            repeatPasswordEditText.setError(getResources().getString(R.string.password_match_error));
            return false;
        }
        return true;
    }

    private void signup() {
        String email = emailEditText.getEditText().getText().toString();
        String password = passwordEditText.getEditText().getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Navigate to Home Screen
                        NavDirections action = SignupFragmentDirections.actionSignupFragmentToHomeFragment();
                        Navigation.findNavController(getView()).navigate(action);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(getActivity(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}