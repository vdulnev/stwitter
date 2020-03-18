package com.dvn.stwitter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;

public class MainActivity extends AppCompatActivity {
    private AuthProvider authProvider = new AuthProvider();
    private MutableLiveData<AuthResult> authResultData = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MainView mainView = findViewById(R.id.main);
        mainView.setText("loading");
        MainActivityViewModel model = new ViewModelProvider(this).get(MainActivityViewModel.class);
        authResultData.observe(this, new Observer<AuthResult>() {
            @Override
            public void onChanged(AuthResult authResult) {
                if (authResult == null) mainView.setText("failed");
                else mainView.setText("success");
            }
        });
        authProvider.getAuthResult(this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                authResultData.postValue(authResult);
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                authResultData.postValue(null);
            }
        });
    }
}
