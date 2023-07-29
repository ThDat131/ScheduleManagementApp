package com.dtn.schedulemanagementapp.models;

import androidx.annotation.NonNull;

public class Category {
    private int id;
    private String name;
    private String color;
    private String username;

    public Category() {
    }

    public Category(int id, String name, String color, String username) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.username = username;
    }

    @NonNull
    @Override
    public String toString() {
        return id + " - " + name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
