package com.dtn.schedulemanagementapp.models;

import java.util.Date;

public class Schedule {
    private int id;
    private String name;
    private String note;
    private Date startDate;
    private Date endDate;
    private int userId;
    private int cateId;

    public Schedule(int id, String name, String note, Date startDate, Date endDate, int userId, int cateId) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }
}
