package com.mahmoudhamdyae.foodplanner.account;

public interface AccountService {

    Boolean hasUser();

    void signInWithGoogle(OnResult onResult);
    void firebaseAuthWithGoogle(String idToken, OnResult onResult);

    void login(String email, String password, OnResult onResult);
    void signup(String email, String password, OnResult onResult);
    void signOut();
}
