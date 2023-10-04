package com.mahmoudhamdyae.foodplanner.account;

import android.app.Activity;

import android.content.Context;
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

    private final FirebaseAuth mAuth;
    private final Context context;

    private final GoogleSignInClient mGoogleSignInClient;

    public AccountServiceImpl(@NonNull Context context) {
        this.context = context;
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Google Sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    @Override
    public Boolean hasUser() {
        return mAuth.getCurrentUser() != null;
    }

    @Override
    public void signInWithGoogle(OnResult onResult) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        onResult.onGoogleAuthSuccess(signInIntent);
    }

    @Override
    public void firebaseAuthWithGoogle(String idToken, OnResult onResult) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.w(TAG, "signInWithGoogle:success");
                onResult.onAuthSuccess();
            } else {
                Log.w(TAG, "signInWithGoogle:failure", task.getException());
                // If sign in fails, display a message to the user.
                onResult.onFailure("Authentication Failed.");
            }
        });
    }

    @Override
    public void login(String email, String password, OnResult onResult) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        onResult.onAuthSuccess();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        onResult.onFailure(String.valueOf(task.getException()));
                    }
                });
    }

    @Override
    public void signup(String email, String password, OnResult onResult) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        onResult.onAuthSuccess();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        onResult.onFailure(String.valueOf(task.getException()));
                    }
                });
    }

    @Override
    public void signOut() {
        mAuth.signOut();
        Log.w(TAG, "signOut");
    }
}
