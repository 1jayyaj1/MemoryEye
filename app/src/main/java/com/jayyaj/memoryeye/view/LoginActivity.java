package com.jayyaj.memoryeye.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.jayyaj.memoryeye.R;
import com.jayyaj.memoryeye.usecase.AuthentificationUseCase;
import com.jayyaj.memoryeye.viewmodel.CurrentUserViewModel;

public class LoginActivity extends AppCompatActivity {

    private AuthentificationUseCase authentificationUseCase;

    private Button signup;
    private Button signin;
    private AutoCompleteTextView email;
    private EditText password;
    private ProgressBar progressBar;

    private CurrentUserViewModel currentUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authentificationUseCase = new AuthentificationUseCase();

        currentUserViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(CurrentUserViewModel.class);

        signup = findViewById(R.id.createAccountSignInButton);
        signin = findViewById(R.id.signInButton);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        progressBar = findViewById(R.id.loginProgress);


        signin.setOnClickListener(v -> {
            signinEmailPasswordUser(email.getText().toString().trim(), password.getText().toString().trim());
        });

        signup.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });
    }

    private void signinEmailPasswordUser(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            authentificationUseCase.signinEmailPasswordUser(currentUserViewModel, email, password).addOnCompleteListener(task -> {
                progressBar.setVisibility(View.INVISIBLE);
                if (task.getResult().getUser() != null) {
                    startActivity(new Intent(LoginActivity.this, HostMenuActivity.class));
                    finish();
                }
            });
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(LoginActivity.this, "Please enter an email and a password", Toast.LENGTH_SHORT).show();
        }
    }
}



