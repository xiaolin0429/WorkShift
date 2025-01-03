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
    private boolean vibrationEnabled;

    public AlarmSettings() {
        this.id = 1;
        this.reminderMinutes = 30;
        this.notificationEnabled = true;
        this.soundEnabled = true;
        this.vibrationEnabled = true;
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

    public boolean isVibrationEnabled() {
        return vibrationEnabled;
    }

    public void setVibrationEnabled(boolean vibrationEnabled) {
        this.vibrationEnabled = vibrationEnabled;
    }
} 