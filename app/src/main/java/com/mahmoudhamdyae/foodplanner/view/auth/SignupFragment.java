package com.mahmoudhamdyae.foodplanner.view.auth;

import android.content.Intent;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.utils.Validation;

public class SignupFragment extends Fragment {

    private static final int RC_SIGN_IN = 1;
    private final String TAG = "SignupFragment";
    private TextInputLayout emailEditText, passwordEditText, repeatPasswordEditText;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

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
        loginButton.setOnClickListener(v -> Navigation.findNavController(view).navigateUp());

        // Skip - Navigate to home screen
        Button skipButton = view.findViewById(R.id.skip_button);
        skipButton.setOnClickListener(v -> {
            NavDirections action = SignupFragmentDirections.actionSignupFragmentToHomeFragment();
            Navigation.findNavController(view).navigate(action);
        });

        // Signup - Navigate to signup screen
        Button signupTextView = view.findViewById(R.id.signup_button);
        signupTextView.setOnClickListener(v -> validateAndSignup());

        // Google Sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        SignInButton googleButton = view.findViewById(R.id.google_sign_in_button);
        googleButton.setOnClickListener(v -> signInWithGoogle());

        // TextInputs
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
        if (validateEmail() && validatePassword() && validateRepeatPassword()) {
            signup();
        }
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

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, /*accessToken=*/ null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                navigateToHomeScreen();
            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(getContext(), "Authentication Failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToHomeScreen() {
        // Navigate to Home Screen
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToHomeFragment();
        Navigation.findNavController(getView()).navigate(action);
    }
}