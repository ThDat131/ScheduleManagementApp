package com.dtn.schedulemanagementapp.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class Schedule implements Serializable {
    private int id;
    private String name;
    private String note;
    private String startDate;
    private String endDate;
    private String username;
    private int cateId;

    public Schedule(int id, String name, String note, String startDate, String endDate, String username, int cateId) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.startDate = startDate;
        this.endDate = endDate;
        this.username = username;
        this.cateId = cateId;
    }

    public Schedule(String name, String note, String startDate, String endDate, String username, int cateId) {
        this.name = name;
        this.note = note;
        this.startDate = startDate;
        this.endDate = endDate;
        this.username = username;
        this.cateId = cateId;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUserId() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }
}
