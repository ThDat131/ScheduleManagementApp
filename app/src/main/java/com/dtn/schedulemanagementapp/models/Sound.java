package com.dtn.schedulemanagementapp.models;

public class Sound {
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

    public String getUrlSound() {
        return urlSound;
    }

    public void setUrlSound(String urlSound) {
        this.urlSound = urlSound;
    }

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
