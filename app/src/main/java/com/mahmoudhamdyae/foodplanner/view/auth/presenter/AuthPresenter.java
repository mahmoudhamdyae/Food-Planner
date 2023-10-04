package com.mahmoudhamdyae.foodplanner.view.auth.presenter;

import android.content.Intent;

import com.mahmoudhamdyae.foodplanner.account.AccountService;
import com.mahmoudhamdyae.foodplanner.account.OnResult;
import com.mahmoudhamdyae.foodplanner.view.auth.view.IAuthView;

public class AuthPresenter implements IAuthPresenter, OnResult {

    private final AccountService accountService;
    private final IAuthView view;

    public AuthPresenter(IAuthView view, AccountService accountService) {
        this.accountService = accountService;
        this.view = view;
    }


    @Override
    public void login(String email, String password) {
        accountService.login(email, password, this);
    }

    @Override
    public void signup(String email, String password) {
        accountService.signup(email, password, this);
    }

    @Override
    public void signInWithGoogle() {
        accountService.signInWithGoogle(this);
    }

    @Override
    public void firebaseAuthWithGoogle(String idToken) {
        accountService.firebaseAuthWithGoogle(idToken, this);
    }

    @Override
    public void onAuthSuccess() {
        view.onAuthSuccess();

    }

    @Override
    public void onGoogleAuthSuccess(Intent signInIntent) {
        view.onGoogleAuthSuccess(signInIntent);
    }

    @Override
    public void onFailure(String errorMsg) {
        view.onFailure(errorMsg);
    }
}
