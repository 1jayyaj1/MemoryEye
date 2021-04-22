package com.jayyaj.memoryeye.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jayyaj.memoryeye.R;

public class HostMenuActivity extends FragmentActivity {
    private final Fragment cameraFragment = new CameraFragment();
    private final Fragment memoriesFragment = new MemoriesFragment();
    private Fragment active;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_menu);

        getSupportFragmentManager().beginTransaction().add(R.id.contentArea, memoriesFragment).hide(memoriesFragment).commit();
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

    private Fragment onFragmentSwitch(Fragment show, Fragment hide) {
        getSupportFragmentManager().beginTransaction().hide(hide).show(show).commit();
        return show;
    }
}