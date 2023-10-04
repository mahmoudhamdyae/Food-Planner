package com.mahmoudhamdyae.foodplanner.view.auth.presenter;

public interface IAuthPresenter {

    void login(String email, String password);
    void signup(String email, String password);

    void signInWithGoogle();
    void firebaseAuthWithGoogle(String idToken);
}
