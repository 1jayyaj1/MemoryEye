package com.jayyaj.memoryeye.model;

import android.net.Uri;

import com.google.firebase.Timestamp;

import java.util.List;

public class Memory {
    private String memoryId;
    private String title;
    private Timestamp date;
    private Type type;
    private String description;
    private String username;
    private String userId;
    private List<String> imagesUrl;

    public Memory() {}

    public Memory(String memoryId, String title, Timestamp date, Type type, String description, String username, String userId, List<String> imagesUrl) {
        this.memoryId = memoryId;
        this.title = title;
        this.date = date;
        this.type = type;
        this.description = description;
        this.username = username;
        this.userId = userId;
        this.imagesUrl = imagesUrl;
    }

    public String getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(String memoryId) {
        this.memoryId = memoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(List<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }
}
