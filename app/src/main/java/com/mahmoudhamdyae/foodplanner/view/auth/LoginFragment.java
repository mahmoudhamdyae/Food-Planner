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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.utils.Validation;

public class LoginFragment extends Fragment {

    private static final int RC_SIGN_IN = 1;
    private final String TAG = "LoginFragment";
    private TextInputLayout emailEditText, passwordEditText;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

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
        skipButton.setOnClickListener(v -> skipAuth());

        // Signup - Navigate to signup screen
        TextView signupTextView = view.findViewById(R.id.signup_free);
        signupTextView.setOnClickListener(v -> {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToSignupFragment();
            Navigation.findNavController(view).navigate(action);
        });

        // Google Sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(view.getContext(), gso);
        SignInButton googleButton = view.findViewById(R.id.google_sign_in_button);
        googleButton.setOnClickListener(v -> signInWithGoogle());

        // TextInputs
        emailEditText = view.findViewById(R.id.email);
        passwordEditText = view.findViewById(R.id.password);

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
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        navigateToHomeScreen();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
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
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
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
        Navigation.findNavController(requireView()).navigate(action);
    }
}