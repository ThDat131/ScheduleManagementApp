package com.dtn.schedulemanagementapp.models;

public class Sound {
    private int id;
    private String name;
    private String urlSound;


    public Sound(int id, String name, String urlSound) {
        this.id = id;
        this.name = name;
        this.urlSound = urlSound;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}
