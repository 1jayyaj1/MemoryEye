package com.jayyaj.memoryeye.model;

import com.google.firebase.Timestamp;

import java.util.List;

public class Memory {
    private String memoryId;
    private String title;
    private Timestamp date;
    private Type type;
    private String description;
    private String creator;
    private List<String> images;

    public Memory() {}

    public Memory(String memoryId, String title, Timestamp date, Type type, String description, String creator, List<String> images) {
        this.memoryId = memoryId;
        this.title = title;
        this.date = date;
        this.type = type;
        this.description = description;
        this.creator = creator;
        this.images = images;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
