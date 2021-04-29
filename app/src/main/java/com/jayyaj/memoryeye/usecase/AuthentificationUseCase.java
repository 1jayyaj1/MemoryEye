package com.jayyaj.memoryeye.usecase;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.jayyaj.memoryeye.viewmodel.CurrentUserViewModel;

import java.util.concurrent.atomic.AtomicBoolean;

public class AuthentificationUseCase {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");

    public AuthentificationUseCase() {
        this.currentUser = getFirebaseAuth().getCurrentUser();
    }

    public FirebaseAuth.AuthStateListener lookupUser(CurrentUserViewModel currentUserViewModel) {
        authStateListener = firebaseAuth -> {
            currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                String currentUserId = currentUser.getUid();
                collectionReference.whereEqualTo("userId", currentUserId)
                        .addSnapshotListener((queryDocumentSnapshots, error) -> {
                            if (error != null) { return; }
                            if (!queryDocumentSnapshots.isEmpty()) {
                                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                                    currentUserViewModel.setUsername(snapshot.getString("username"));
                                    currentUserViewModel.setUserId(snapshot.getString("userId"));
                                }
                            }
                        });
            }
        };
        return authStateListener;
    }

    public FirebaseAuth getFirebaseAuth() {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }
}
