package com.shiftschedule.app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shift_types")
public class ShiftType {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;          // 班次名称
    private int color;           // 班次颜色
    private long defaultStartTime; // 默认上班时间
    private long defaultEndTime;   // 默认下班时间
    private long breakStartTime;   // 默认休息开始时间
    private long breakEndTime;     // 默认休息结束时间
    private boolean isDefault;     // 是否默认班次
    private int sortOrder;        // 排序顺序

    public ShiftType(String name, int color) {
        this.name = name;
        this.color = color;
    }

    // Getters and Setters
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public long getDefaultStartTime() {
        return defaultStartTime;
    }

    public void setDefaultStartTime(long defaultStartTime) {
        this.defaultStartTime = defaultStartTime;
    }

    public long getDefaultEndTime() {
        return defaultEndTime;
    }

    public void setDefaultEndTime(long defaultEndTime) {
        this.defaultEndTime = defaultEndTime;
    }

    public long getBreakStartTime() {
        return breakStartTime;
    }

    public void setBreakStartTime(long breakStartTime) {
        this.breakStartTime = breakStartTime;
    }

    public long getBreakEndTime() {
        return breakEndTime;
    }

    public void setBreakEndTime(long breakEndTime) {
        this.breakEndTime = breakEndTime;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
} 