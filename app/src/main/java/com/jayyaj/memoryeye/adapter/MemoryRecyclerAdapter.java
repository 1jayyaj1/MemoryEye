package com.jayyaj.memoryeye.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jayyaj.memoryeye.R;
import com.jayyaj.memoryeye.model.Memory;

import java.util.ArrayList;
import java.util.List;

public class MemoryRecyclerAdapter extends RecyclerView.Adapter<MemoryRecyclerAdapter.ViewHolder> {
    private List<Memory> memories = new ArrayList<>();
    private Context context;

    public MemoryRecyclerAdapter(List<Memory> memories, Context context) {
        this.memories = memories;
        this.context = context;
    }

    @NonNull
    @Override
    public MemoryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.memory_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoryRecyclerAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return memories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
