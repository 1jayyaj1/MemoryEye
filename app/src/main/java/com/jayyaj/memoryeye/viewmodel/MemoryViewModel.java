package com.jayyaj.memoryeye.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.jayyaj.memoryeye.data.MemoryRepository;
import com.jayyaj.memoryeye.model.Memory;

import java.util.List;

public class MemoryViewModel extends AndroidViewModel {
    private static final String TAG = "MemoryViewModel";
    MemoryRepository memoryRepository = new MemoryRepository();
    MutableLiveData<List<Memory>> memories = new MutableLiveData<>();

    public MemoryViewModel(@NonNull Application application) {
        super(application);
    }

    public void createMemory(Memory memory) {
        memoryRepository.createMemory(memory);
    }
}
