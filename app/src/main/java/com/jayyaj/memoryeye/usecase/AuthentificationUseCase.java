package com.jayyaj.memoryeye.usecase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.jayyaj.memoryeye.viewmodel.CurrentUserViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    public Task<AuthResult> signinEmailPasswordUser(CurrentUserViewModel currentUserViewModel, String email, String password) {
        return firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (currentUser != null) {
                        String currentUserId = currentUser.getUid();
                        collectionReference.whereEqualTo("userId", currentUserId)
                            .addSnapshotListener((queryDocumentSnapshots, error) -> {
                                if (error != null) {
                                }
                                assert queryDocumentSnapshots != null;
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                                        currentUserViewModel.setUsername(snapshot.getString("username"));
                                        currentUserViewModel.setUserId(currentUserId);
                                    }
                                }
                            });
                    }
                }).addOnFailureListener(e -> {
        });
    }

    public Task<AuthResult> createUserEmailAccount(CurrentUserViewModel currentUserViewModel, String emailText, String passwordText, String usernameText) {
        return firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //We take users to the AddJournalActivity
                currentUser = firebaseAuth.getCurrentUser();
                assert currentUser != null;
                String currentUserId = currentUser.getUid();

                //Creating user Map to be added to collection
                Map<String, String> userObj = new HashMap<>();
                userObj.put("userId", currentUserId);
                userObj.put("username", usernameText);

                //Save to firestore
                collectionReference.add(userObj).addOnSuccessListener(documentReference -> {
                    documentReference.get().addOnCompleteListener(task1 -> {
                        if (Objects.requireNonNull(task1.getResult()).exists()) {
                            String name = task1.getResult().getString("username");

                            currentUserViewModel.setUsername(name);
                            currentUserViewModel.setUserId(currentUserId);
                        } else {
                        }
                    }).addOnFailureListener(e -> {
                    });
                }).addOnFailureListener(e -> {
                });
            } else {
            }
        }).addOnFailureListener(e -> {
        });
    }

    public FirebaseAuth getFirebaseAuth() {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }
}
