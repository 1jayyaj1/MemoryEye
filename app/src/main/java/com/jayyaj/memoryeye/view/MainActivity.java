package com.jayyaj.memoryeye.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.jayyaj.memoryeye.R;
import com.jayyaj.memoryeye.usecase.AuthentificationUseCase;
import com.jayyaj.memoryeye.viewmodel.CurrentUserViewModel;

public class MainActivity extends AppCompatActivity {

    private AuthentificationUseCase authentificationUseCase;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    private CurrentUserViewModel currentUserViewModel;

    private Button getStartedButton;

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authentificationUseCase = new AuthentificationUseCase();

        currentUserViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(CurrentUserViewModel.class);

        firebaseAuth = authentificationUseCase.getFirebaseAuth();

        authStateListener = authentificationUseCase.lookupUser(currentUserViewModel);

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, HostMenuActivity.class));
            finish();
        }

        getStartedButton = findViewById(R.id.getStartedButton);

        getStartedButton.setOnClickListener(v -> {
            if (hasPermission()) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            } else {
                requestPermission();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            StringBuilder errorText = new StringBuilder();
            for (int i = 0; i < PERMISSIONS.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    errorText.append(" ").append(permissions[i].toLowerCase());
                }
            }
            if (errorText.length() > 0) {
                Toast.makeText(MainActivity.this, errorText, Toast.LENGTH_SHORT).show();
                requestPermission();
            }
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE);
    }

    private boolean hasPermission() {
        for (String perm : PERMISSIONS) {
            boolean decision = ContextCompat.checkSelfPermission(this, perm)
                    == PackageManager.PERMISSION_GRANTED;
            if (!decision)
                return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (firebaseAuth != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}