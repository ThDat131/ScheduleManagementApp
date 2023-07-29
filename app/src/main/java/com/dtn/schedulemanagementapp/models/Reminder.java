package com.dtn.schedulemanagementapp.models;

import java.io.Serializable;
import java.util.Date;

public class Reminder implements Serializable {
    private int id;
    private String nameRemind;
    private String timeRemind;
    private int scheduleId;
    private int soundId;

    public Reminder(int id, String nameRemind, String timeRemind, int scheduleId, int soundId) {
        this.id = id;
        this.nameRemind = nameRemind;
        this.timeRemind = timeRemind;
        this.scheduleId = scheduleId;
        this.soundId = soundId;
    }

    public Reminder(String nameRemind, String timeRemind, int scheduleId, int soundId) {
        this.nameRemind = nameRemind;
        this.timeRemind = timeRemind;
        this.scheduleId = scheduleId;
        this.soundId = soundId;
    }
    public Reminder(String nameRemind, String timeRemind, int scheduleId) {
        this.nameRemind = nameRemind;
        this.timeRemind = timeRemind;
        this.scheduleId = scheduleId;
    }

    @Override
    public String toString() {
        return id + " - " + nameRemind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameRemind() {
        return nameRemind;
    }

    public void setNameRemind(String nameRemind) {
        this.nameRemind = nameRemind;
    }

    public String getTimeRemind() {
        return timeRemind;
    }

    public void setTimeRemind(String timeRemind) {
        this.timeRemind = timeRemind;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getSoundId() {
        return soundId;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
    }
}
