package com.shiftschedule.app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shift_schedules")
public class ShiftSchedule {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date;
    private String shiftType;
    private long startTime;
    private long endTime;
    private String note;

    public ShiftSchedule() {
        this.note = "";
    }

    public ShiftSchedule(String date, String shiftType, String startTime, String endTime, long startTimeMillis, long endTimeMillis) {
        this.date = date;
        this.shiftType = shiftType;
        this.startTime = startTimeMillis;
        this.endTime = endTimeMillis;
        this.note = "";
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
} 