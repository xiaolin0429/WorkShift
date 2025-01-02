package com.shiftschedule.app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarm_settings")
public class AlarmSettings {
    @PrimaryKey
    private int id;
    private int reminderMinutes;
    private boolean notificationEnabled;
    private boolean soundEnabled;
    private boolean vibrateEnabled;

    public AlarmSettings(int reminderMinutes, boolean notificationEnabled, boolean soundEnabled, boolean vibrateEnabled) {
        this.id = 1; // 只保存一条记录
        this.reminderMinutes = reminderMinutes;
        this.notificationEnabled = notificationEnabled;
        this.soundEnabled = soundEnabled;
        this.vibrateEnabled = vibrateEnabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReminderMinutes() {
        return reminderMinutes;
    }

    public void setReminderMinutes(int reminderMinutes) {
        this.reminderMinutes = reminderMinutes;
    }

    public boolean isNotificationEnabled() {
        return notificationEnabled;
    }

    public void setNotificationEnabled(boolean notificationEnabled) {
        this.notificationEnabled = notificationEnabled;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
    }

    public boolean isVibrateEnabled() {
        return vibrateEnabled;
    }

    public void setVibrateEnabled(boolean vibrateEnabled) {
        this.vibrateEnabled = vibrateEnabled;
    }
} 