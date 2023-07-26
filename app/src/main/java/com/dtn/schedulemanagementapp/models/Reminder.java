package com.dtn.schedulemanagementapp.models;

import java.util.Date;

public class Reminder {
    private int id;
    private String nameRemind;
    private Date timeRemind;
    private int scheduleId;
    private int soundId;

    public Reminder(int id, String nameRemind, Date timeRemind, int scheduleId, int soundId) {
        this.id = id;
        this.nameRemind = nameRemind;
        this.timeRemind = timeRemind;
        this.scheduleId = scheduleId;
        this.soundId = soundId;
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

    public Date getTimeRemind() {
        return timeRemind;
    }

    public void setTimeRemind(Date timeRemind) {
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
