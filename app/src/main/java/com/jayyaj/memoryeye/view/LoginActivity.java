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

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            progressBar.setVisibility(View.VISIBLE);
            authentificationUseCase.signinEmailPasswordUser(this, currentUserViewModel, email, password).addOnCompleteListener(task -> {
                progressBar.setVisibility(View.INVISIBLE);
                if (task.getResult().getUser() != null) {
                    startActivity(new Intent(LoginActivity.this, HostMenuActivity.class));
                    finish();
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, "Please enter an email and a password", Toast.LENGTH_SHORT).show();
        }
    }
}



