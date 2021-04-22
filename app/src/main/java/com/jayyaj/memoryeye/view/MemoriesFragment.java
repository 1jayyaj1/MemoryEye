package com.jayyaj.memoryeye.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jayyaj.memoryeye.R;

public class MemoriesFragment extends Fragment {
    private FloatingActionButton createMemory;

    public MemoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static MemoriesFragment newInstance() {
        MemoriesFragment fragment = new MemoriesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_memories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createMemory = view.findViewById(R.id.createMemory);

        createMemory.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MemoryCreateActivity.class));
        });
    }
}