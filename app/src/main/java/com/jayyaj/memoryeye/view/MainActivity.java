package com.jayyaj.memoryeye.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.jayyaj.memoryeye.R;

public class MainActivity extends AppCompatActivity {
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

        getStartedButton = findViewById(R.id.getStartedButton);

        getStartedButton.setOnClickListener(v -> {
            if (hasPermission()) {
                startActivity(new Intent(MainActivity.this, HostMenuActivity.class));
            } else {
                requestPermission();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("ok", String.valueOf(requestCode));
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
}