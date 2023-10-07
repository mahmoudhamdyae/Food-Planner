package com.mahmoudhamdyae.foodplanner.view.auth.view;

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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.mahmoudhamdyae.foodplanner.LoadingDialog;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.account.AccountServiceImpl;
import com.mahmoudhamdyae.foodplanner.db.LocalDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.model.RepositoryImpl;
import com.mahmoudhamdyae.foodplanner.network.RemoteDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.utils.Validation;
import com.mahmoudhamdyae.foodplanner.view.auth.presenter.AuthPresenter;
import com.mahmoudhamdyae.foodplanner.view.auth.presenter.IAuthPresenter;

public class LoginFragment extends Fragment implements IAuthView {

    private TextInputLayout emailEditText, passwordEditText;
    private IAuthPresenter presenter;
    private LoadingDialog loadingDialog;

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

        loadingDialog = new LoadingDialog(getActivity());

        presenter = new AuthPresenter(this, new AccountServiceImpl(requireContext()), RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(),
                LocalDataSourceImpl.getInstance(requireContext())));

        // Login
        Button loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> validateAndLogin());

        // Skip - Navigate to home screen
        Button skipButton = view.findViewById(R.id.skip_button);
        skipButton.setOnClickListener(v -> skipAuth());

        // Signup - Navigate to signup screen
        TextView signupTextView = view.findViewById(R.id.signup_free);
        signupTextView.setOnClickListener(v -> {
            navigateToSignupScreen();
        });

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
        loadingDialog.startLoadingDialog();
        String email = emailEditText.getEditText().getText().toString();
        String password = passwordEditText.getEditText().getText().toString();
        presenter.login(email, password);
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

    private void navigateToSignupScreen() {
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToSignupFragment();
        Navigation.findNavController(requireView()).navigate(action);
    }

    private void navigateToHomeScreen() {
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToHomeFragment();
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onAuthSuccess() {
        // Log in success
        loadingDialog.dismissDialog();
        navigateToHomeScreen();
    }

    @Override
    public void onGoogleAuthSuccess(Intent signInIntent) { }

    @Override
    public void onFailure(String errorMsg) {
        // Failed to log in
        loadingDialog.dismissDialog();
        Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
    }
}