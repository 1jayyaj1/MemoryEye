package com.jayyaj.memoryeye.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jayyaj.memoryeye.R;

public class HostMenuActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    private final Fragment cameraFragment = new CameraFragment();
    private final Fragment memoriesFragment = new MemoriesFragment();
    private Fragment active;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_menu);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        getSupportFragmentManager().beginTransaction().add(R.id.contentArea, memoriesFragment).show(memoriesFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.contentArea, cameraFragment).show(cameraFragment).commit();

        active = cameraFragment;

        BottomNavigationView bottomNavigationView =
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.eye_nav_button:
                    if (cameraFragment != active)
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.contentArea, cameraFragment)
                                .hide(memoriesFragment)
                                .commit();
                        active = cameraFragment;
                    return true;

                case R.id.memories_nav_button:
                    if (memoriesFragment != active)
                        getSupportFragmentManager().beginTransaction()
                                .remove(cameraFragment)
                                .commit();
                        getSupportFragmentManager().beginTransaction()
                                .show(memoriesFragment)
                                .commit();
                        active = memoriesFragment;
                    return true;
            }
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.actionSignOut:
                if (user != null && firebaseAuth != null) {
                    firebaseAuth.signOut();
                    startActivity(new Intent(HostMenuActivity.this, MainActivity.class));
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}