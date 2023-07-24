package com.dtn.schedulemanagementapp.models;

import java.util.Date;

public class Schedule {
    private int id;
    private String name;
    private String note;
    private Date startDate;
    private Date endDate;
    private String username;
    private int cateId;

    public Schedule(int id, String name, String note, Date startDate, Date endDate, String username, int cateId) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.startDate = startDate;
        this.endDate = endDate;
        this.username = username;
        this.cateId = cateId;
    }

    public Schedule(String name, String note, Date startDate, Date endDate, String username, int cateId) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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
