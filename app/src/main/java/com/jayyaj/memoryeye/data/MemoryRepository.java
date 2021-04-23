package com.jayyaj.memoryeye.data;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.jayyaj.memoryeye.model.Memory;

import java.util.ArrayList;
import java.util.List;

public class MemoryRepository {
    private static final String TAG = "MemoryRepository";
    private static final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference collectionReference = firebaseFirestore.collection("Memories");

//    public List<Memory> getAllMemories() {}
//
//    public Memory getMemory(String memoryId) {}
//
//    public void deleteAllMemories() {}
//
//    public void deleteMemory(String memoryId) {}
//
      public void createMemory(Memory memory) {
          for (String image : memory.getImages()) {
              Uri imageUri = Uri.parse(image);
              StorageReference filePath = storageReference
                      .child("memory_image")
                      .child(memory.getMemoryId() + "_img_" + Timestamp.now().getSeconds());

              filePath.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                  filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                      String imageUrl = uri.toString();
                      //journal.setTimeAdded(new Timestamp(new Date()));

                      collectionReference.add(memory).addOnSuccessListener(documentReference -> {
                          Log.d(TAG, "Successfully created memory");
                      }).addOnFailureListener(e -> {
                          Log.e(TAG, "Could not create memory");
                      });
                  }).addOnFailureListener(e -> {
                      Log.e(TAG, "Could not create memory");
                  });
              }).addOnFailureListener(e -> {
                  Log.e(TAG, "Could not create memory");
              });
          }
      }
}
