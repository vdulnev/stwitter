package com.dvn.stwitter;

import android.app.Activity;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

public class AuthProvider {

    @Nullable private AuthResult authResult;

    void getAuthResult(Activity activity, OnSuccessListener<AuthResult> onSuccessListener, OnFailureListener onFailureListener) {
        if (authResult != null) {
            onSuccessListener.onSuccess(authResult);
        }
        else auth(activity, onSuccessListener, onFailureListener);
    }

    private void auth(Activity activity, OnSuccessListener<AuthResult> onSuccessListener, OnFailureListener onFailureListener) {
        OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Task<AuthResult> pendingResultTask = firebaseAuth.getPendingAuthResult();
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(onSuccessListener)
                    .addOnFailureListener(onFailureListener);
        } else {
            firebaseAuth
                    .startActivityForSignInWithProvider(/* activity= */ activity, provider.build())
                    .addOnSuccessListener(onSuccessListener)
                    .addOnFailureListener(onFailureListener);
        }
    }
}
