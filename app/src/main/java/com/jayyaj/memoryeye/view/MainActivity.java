package com.jayyaj.memoryeye.view;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jayyaj.memoryeye.R;

public class MainActivity extends FragmentActivity {
    final Fragment fragment2 = new CameraFragment();
    final Fragment fragment3 = new MemoriesFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm.beginTransaction().add(R.id.contentArea, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.contentArea, fragment2, "2").hide(fragment2).commit();


        BottomNavigationView bottomNavigationView =
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.eye_nav_button:
                    findViewById(R.id.memoriesFragment).setVisibility(View.GONE);
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                    return true;

                case R.id.memories_nav_button:
                    findViewById(R.id.cameraFragment).setVisibility(View.GONE);
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    active = fragment3;
                    return true;
            }
            return false;
        });
    }
}