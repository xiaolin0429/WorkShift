package com.shiftschedule.app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rotation_patterns")
public class RotationPattern {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int groupCount;
    private int cycleDays;
    private int durationDays;
    private boolean isActive;

    public RotationPattern(String name, int groupCount, int cycleDays, int durationDays) {
        this.name = name;
        this.groupCount = groupCount;
        this.cycleDays = cycleDays;
        this.durationDays = durationDays;
        this.isActive = false;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RotationPattern pattern = (RotationPattern) o;

        if (id != pattern.id) return false;
        if (groupCount != pattern.groupCount) return false;
        if (cycleDays != pattern.cycleDays) return false;
        if (durationDays != pattern.durationDays) return false;
        if (isActive != pattern.isActive) return false;
        return name != null ? name.equals(pattern.name) : pattern.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + groupCount;
        result = 31 * result + cycleDays;
        result = 31 * result + durationDays;
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }
} 