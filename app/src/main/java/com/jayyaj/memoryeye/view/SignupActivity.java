package com.jayyaj.memoryeye.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jayyaj.memoryeye.R;
import com.jayyaj.memoryeye.usecase.AuthentificationUseCase;
import com.jayyaj.memoryeye.viewmodel.CurrentUserViewModel;
import com.jayyaj.memoryeye.viewmodel.MemoryViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    private AuthentificationUseCase authentificationUseCase;

    private static final String TAG = "SignupActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;


    private CurrentUserViewModel currentUserViewModel;

    private Button signup;
    private TextView username;
    private AutoCompleteTextView email;
    private TextView password;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        authentificationUseCase = new AuthentificationUseCase();

        currentUserViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(CurrentUserViewModel.class);

        firebaseAuth = authentificationUseCase.getFirebaseAuth();

        username = findViewById(R.id.usernameSignup);
        email = findViewById(R.id.emailSignup);
        password = findViewById(R.id.passwordSignup);
        signup = findViewById(R.id.createAccountButton);
        progressBar = findViewById(R.id.signupProgress);

        authStateListener = firebaseAuth -> {
            currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                //User is already logged in
            } else {
                //No users are logged in
            }
        };

        signup.setOnClickListener(v -> {
            String emailText = email.getText().toString().trim();
            String usernameText = username.getText().toString().trim();
            String passwordText = password.getText().toString().trim();

            createUserEmailAccount(emailText, passwordText, usernameText);
        });

    }

    private void createUserEmailAccount(String emailText, String passwordText, String usernameText) {
        if (!TextUtils.isEmpty(emailText) && !TextUtils.isEmpty(passwordText) && !TextUtils.isEmpty(usernameText)) {
            progressBar.setVisibility(View.VISIBLE);
            authentificationUseCase.createUserEmailAccount(currentUserViewModel, emailText, passwordText, usernameText).addOnCompleteListener(task -> {
                progressBar.setVisibility(View.INVISIBLE);
                if (task.getResult().getUser() != null) {
                    startActivity(new Intent(SignupActivity.this, HostMenuActivity.class));
                    finish();
                }
            });

        } else {
            Toast.makeText(SignupActivity.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}