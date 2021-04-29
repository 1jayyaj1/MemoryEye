package com.jayyaj.memoryeye.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CurrentUserViewModel extends ViewModel {
    private MutableLiveData<String> selectedUsername = new MutableLiveData<>();
    private MutableLiveData<String> selectedUserId = new MutableLiveData<>();

    public MutableLiveData<String> getUsername() {
        return selectedUsername;
    }

    public void setUsername(String username) { selectedUsername.setValue(username); }

    public MutableLiveData<String> getUserId() { return selectedUserId; }

    public void setUserId(String userId) {
        selectedUserId.setValue(userId);
    }
}
