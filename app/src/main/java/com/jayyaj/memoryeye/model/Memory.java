package com.jayyaj.memoryeye.model;

import com.google.firebase.Timestamp;

public class Memory {
    private String title;
    private Timestamp date;
    private Type type;
    private String description;
    private String creator;

    public Memory() {}

    public Memory(String title, Timestamp date, Type type, String description, String creator) {
        this.title = title;
        this.date = date;
        this.type = type;
        this.description = description;
        this.creator = creator;
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
}
