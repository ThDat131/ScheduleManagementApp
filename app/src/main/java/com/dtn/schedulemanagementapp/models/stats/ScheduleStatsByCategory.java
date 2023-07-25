package com.dtn.schedulemanagementapp.models.stats;

public class ScheduleStatsByCategory {

    public ScheduleStatsByCategory(String name, int qty) {
        this.name = name;
        this.qty = qty;
    }

    private String name;
    private int qty;

    public String getName() {
        return name;
    }

    public int getQty() {
        return qty;
    }
}
