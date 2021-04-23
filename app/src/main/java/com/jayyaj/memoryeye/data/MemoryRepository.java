package com.jayyaj.memoryeye.data;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.jayyaj.memoryeye.model.Memory;

import java.util.List;

public class MemoryRepository {
    private static final String TAG = "MemoryRepository";
    private static final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference collectionReference = firebaseFirestore.collection("Journal");

//    public List<Memory> getAllMemories() {}
//
//    public Memory getMemory(String memoryId) {}
//
//    public void deleteAllMemories() {}
//
//    public void deleteMemory(String memoryId) {}
//
//    public void createMemory(Memory memory) {}
}
