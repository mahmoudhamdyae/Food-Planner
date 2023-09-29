package com.mahmoudhamdyae.foodplanner.account;

public interface AccountService {

    Boolean hasUser();

    void signInWithGoogle();
    void firebaseAuthWithGoogle(String idToken);

    void login(String email, String password);
    void signup(String email, String password);
    void signOut();
}
