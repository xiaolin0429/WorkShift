package com.shiftschedule.app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.annotation.NonNull;

@Entity(tableName = "rotation_patterns")
public class RotationPattern {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    @NonNull
    private String name = "";

    @ColumnInfo(name = "pattern")
    @NonNull
    private String pattern = "";

    @ColumnInfo(name = "is_active")
    private boolean isActive = false;

    @ColumnInfo(name = "days_count")
    private int daysCount = 0;

    @ColumnInfo(name = "group_count")
    private int groupCount = 0;

    @ColumnInfo(name = "cycle_days")
    private int cycleDays = 0;

    @ColumnInfo(name = "duration_days")
    private int durationDays = 0;

    public RotationPattern() {
    }

    @Ignore
    public RotationPattern(@NonNull String name, @NonNull String pattern, int daysCount) {
        this.name = name;
        this.pattern = pattern;
        this.daysCount = daysCount;
    }

    @Ignore
    public RotationPattern(@NonNull String name, int groupCount, int cycleDays, int durationDays) {
        this.name = name;
        this.groupCount = groupCount;
        this.cycleDays = cycleDays;
        this.durationDays = durationDays;
        this.pattern = generatePattern();
    }

    private String generatePattern() {
        // 根据groupCount, cycleDays和durationDays生成pattern
        StringBuilder pattern = new StringBuilder();
        // TODO: 实现具体的pattern生成逻辑
        return pattern.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getPattern() {
        return pattern;
    }

    public void setPattern(@NonNull String pattern) {
        this.pattern = pattern;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(int daysCount) {
        this.daysCount = daysCount;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

    public int getCycleDays() {
        return cycleDays;
    }

    public void setCycleDays(int cycleDays) {
        this.cycleDays = cycleDays;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }
} 