package com.mahmoudhamdyae.foodplanner.account;

import android.app.Activity;
<<<<<<< HEAD
=======
import android.content.Context;
>>>>>>> origin/master
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mahmoudhamdyae.foodplanner.R;

public class AccountServiceImpl implements AccountService {

    private static final String TAG = "Account_Service";
    private static AccountService accountService = null;
    private final OnResult listener;

    private final FirebaseAuth mAuth;
<<<<<<< HEAD
    private final Activity context;

    private final GoogleSignInClient mGoogleSignInClient;

    private AccountServiceImpl(@NonNull Activity context, OnResult listener) {
=======
    private final Context context;

    private GoogleSignInClient mGoogleSignInClient;

    private AccountServiceImpl(@NonNull Context context, OnResult listener) {
>>>>>>> origin/master
        this.context = context;
        this.listener = listener;
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Google Sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

<<<<<<< HEAD
    public static AccountService getInstance(@NonNull Activity context, OnResult listener) {
=======
    public static AccountService getInstance(@NonNull Context context, OnResult listener) {
>>>>>>> origin/master
        if (accountService == null) accountService = new AccountServiceImpl(context, listener);
        return accountService;
    }

    @Override
    public Boolean hasUser() {
        return mAuth.getCurrentUser() != null;
    }

    @Override
    public void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        listener.onGoogleAuthSuccess(signInIntent);
    }

    @Override
    public void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                listener.onAuthSuccess();
            } else {
                // If sign in fails, display a message to the user.
                listener.onFailure("Authentication Failed.");
            }
        });
    }

    @Override
    public void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        listener.onAuthSuccess();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        listener.onFailure(String.valueOf(task.getException()));
                    }
                });
    }

    @Override
    public void signup(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        listener.onAuthSuccess();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        listener.onFailure(String.valueOf(task.getException()));
                    }
                });
    }

    @Override
    public void signOut() {
        mAuth.signOut();
    }
}
