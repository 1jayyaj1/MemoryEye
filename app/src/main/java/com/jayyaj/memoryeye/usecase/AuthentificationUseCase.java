package com.jayyaj.memoryeye.usecase;

import android.util.Log;

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

    private static final String TAG = "AuthentificationUseCase: ";

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
                        if (error != null) {
                            Log.e(TAG, "Could not find user");
                            return;
                        }
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
                                Log.e(TAG, "Could not find user");
                                return;
                            }
                            if (!queryDocumentSnapshots.isEmpty()) {
                                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                                    currentUserViewModel.setUsername(snapshot.getString("username"));
                                    currentUserViewModel.setUserId(currentUserId);
                                }
                            }
                        });
                }
            }).addOnFailureListener(e -> {
                Log.e(TAG, "Could not sign in with email and password");
        });
    }

    public Task<AuthResult> createUserEmailAccount(CurrentUserViewModel currentUserViewModel, String emailText, String passwordText, String usernameText) {
        return firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                currentUser = firebaseAuth.getCurrentUser();
                assert currentUser != null;
                String currentUserId = currentUser.getUid();

                Map<String, String> userObj = new HashMap<>();
                userObj.put("userId", currentUserId);
                userObj.put("username", usernameText);

                collectionReference.add(userObj).addOnSuccessListener(documentReference -> {
                    documentReference.get().addOnCompleteListener(task1 -> {
                        if (Objects.requireNonNull(task1.getResult()).exists()) {
                            String name = task1.getResult().getString("username");

                            currentUserViewModel.setUsername(name);
                            currentUserViewModel.setUserId(currentUserId);
                        } else {
                            Log.e(TAG, "Document reference contains an empty result");
                        }
                    }).addOnFailureListener(e -> {
                        Log.e(TAG, "Could not get document reference upon user creation");
                    });
                }).addOnFailureListener(e -> {
                    Log.e(TAG, "Could not add new user to collection reference");
                });
            } else {
                Log.e(TAG, "Create user task was not successful");
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Could not create user with email and password");
        });
    }

    public FirebaseAuth getFirebaseAuth() {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }
}
