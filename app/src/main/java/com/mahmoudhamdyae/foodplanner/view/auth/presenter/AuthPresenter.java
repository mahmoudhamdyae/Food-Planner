package com.mahmoudhamdyae.foodplanner.view.auth.presenter;

import android.content.Intent;
import android.util.Log;

import com.mahmoudhamdyae.foodplanner.account.AccountService;
import com.mahmoudhamdyae.foodplanner.account.OnResult;
import com.mahmoudhamdyae.foodplanner.model.Meal;
import com.mahmoudhamdyae.foodplanner.model.Repository;
import com.mahmoudhamdyae.foodplanner.network.NetworkCallback;
import com.mahmoudhamdyae.foodplanner.view.auth.view.IAuthView;

public class AuthPresenter implements IAuthPresenter, OnResult, NetworkCallback {

    private final AccountService accountService;
    private final IAuthView view;
    private final Repository repo;

    public AuthPresenter(IAuthView view, AccountService accountService, Repository repo) {
        this.accountService = accountService;
        this.view = view;
        this.repo = repo;
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
        repo.getFavMealsFromFirebase(this);
    }

    @Override
    public void onGoogleAuthSuccess(Intent signInIntent) {
        view.onGoogleAuthSuccess(signInIntent);
    }

    @Override
    public void onFailure(String errorMsg) {
        view.onFailure(errorMsg);
    }

    @Override
    public void onSuccessResult(Object object) {
        Log.d("hahahahaha", "onSuccessResult: " + ((Meal) object).getName());
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.onFailure(errorMsg);
    }
}
