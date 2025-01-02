package com.shiftschedule.app.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "rotation_days",
        foreignKeys = @ForeignKey(
            entity = RotationPattern.class,
            parentColumns = "id",
            childColumns = "patternId",
            onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("patternId")})
public class RotationDay {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int patternId;
    private int dayIndex;
    private int shiftTypeIndex;
    private int groupIndex;
    private String shiftType;
    private long startTime;
    private long endTime;

    // 主构造函数 - 用于Room
    public RotationDay(int patternId, int dayIndex, String shiftType, long startTime, long endTime) {
        this.patternId = patternId;
        this.dayIndex = dayIndex;
        this.shiftType = shiftType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // 次要构造函数 - 用于轮班模式设置
    @Ignore
    public RotationDay(int dayIndex, int shiftTypeIndex, int groupIndex) {
        this.dayIndex = dayIndex;
        this.shiftTypeIndex = shiftTypeIndex;
        this.groupIndex = groupIndex;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatternId() {
        return patternId;
    }

    public void setPatternId(int patternId) {
        this.patternId = patternId;
    }

    public int getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
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

    public int getShiftTypeIndex() {
        return shiftTypeIndex;
    }

    public void setShiftTypeIndex(int shiftTypeIndex) {
        this.shiftTypeIndex = shiftTypeIndex;
    }

    public int getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(int groupIndex) {
        this.groupIndex = groupIndex;
    }
} 