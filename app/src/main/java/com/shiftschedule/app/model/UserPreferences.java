package com.shiftschedule.app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "user_preferences")
public class UserPreferences {
    @PrimaryKey
    private int id = 1;  // 只保存一条记录

    @ColumnInfo(name = "theme_mode")
    private String themeMode = "system";  // system, light, dark

    @ColumnInfo(name = "show_lunar_date")
    private boolean showLunarDate = true;

    @ColumnInfo(name = "first_day_of_week")
    private int firstDayOfWeek = 1;  // 1 = Monday, 7 = Sunday

    @ColumnInfo(name = "default_shift_duration")
    private int defaultShiftDuration = 8;  // 默认班次时长（小时）

    public UserPreferences() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThemeMode() {
        return themeMode;
    }

    public void setThemeMode(String themeMode) {
        this.themeMode = themeMode;
    }

    public boolean isShowLunarDate() {
        return showLunarDate;
    }

    public void setShowLunarDate(boolean showLunarDate) {
        this.showLunarDate = showLunarDate;
    }

    public int getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    public void setFirstDayOfWeek(int firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
    }

    public int getDefaultShiftDuration() {
        return defaultShiftDuration;
    }

    public void setDefaultShiftDuration(int defaultShiftDuration) {
        this.defaultShiftDuration = defaultShiftDuration;
    }
} 