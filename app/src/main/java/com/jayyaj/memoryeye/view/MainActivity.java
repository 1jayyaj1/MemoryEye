package com.jayyaj.memoryeye.view;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jayyaj.memoryeye.R;

public class MainActivity extends FragmentActivity {
    private final Fragment cameraFragment = new CameraFragment();
    private final Fragment memoriesFragment = new MemoriesFragment();
    private Fragment active = cameraFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.contentArea, memoriesFragment).hide(memoriesFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.contentArea, cameraFragment).hide(cameraFragment).commit();

        active = onFragmentSwitch(cameraFragment, active);

        BottomNavigationView bottomNavigationView =
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.eye_nav_button:
                    active = onFragmentSwitch(cameraFragment, active);
                    return true;

                case R.id.memories_nav_button:
                    active = onFragmentSwitch(memoriesFragment, active);
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