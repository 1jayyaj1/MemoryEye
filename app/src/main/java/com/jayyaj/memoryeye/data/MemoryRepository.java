package com.jayyaj.memoryeye.data;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jayyaj.memoryeye.model.Memory;
import com.jayyaj.memoryeye.usecase.AuthentificationUseCase;

import java.util.ArrayList;
import java.util.List;

public class MemoryRepository {
    private static final String TAG = "MemoryRepository";

    private static final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private CollectionReference collectionReference = firebaseFirestore.collection("Memories");

    private AuthentificationUseCase authentificationUseCase = new AuthentificationUseCase();
//    public List<Memory> getAllMemories() {}
//
//    public Memory getMemory(String memoryId) {}
//
//    public void deleteAllMemories() {}
//
//    public void deleteMemory(String memoryId) {}
//
      public void createMemory(Memory memory) {
          for (String imageUrl : memory.getImagesUrl()) {
              Log.d(TAG, "Count");
              StorageReference filePath = storageReference
                      .child("memory_image")
                      .child(authentificationUseCase.getFirebaseAuth().getCurrentUser().getUid() + "_img_" + Timestamp.now().getSeconds());

              filePath.putFile(Uri.parse(imageUrl)).addOnSuccessListener(taskSnapshot -> {
                  filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                      Log.d(TAG, "Successfully created image(s) of memory");
                  }).addOnFailureListener(e -> {
                      Log.e(TAG, "Could not get download url for image(s) of memory");
                  });
              }).addOnFailureListener(e -> {
                  Log.e(TAG, "Could not create image(s) for memory");
              });
          }
          collectionReference.add(memory).addOnSuccessListener(documentReference -> {
              Log.d(TAG, "Successfully created memory");
          }).addOnFailureListener(e -> {
              Log.e(TAG, "Could not create memory");
          });
      }
}
