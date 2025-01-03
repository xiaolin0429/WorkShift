package com.shiftschedule.app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.annotation.NonNull;
import java.util.Objects;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(
    tableName = "shift_schedules",
    indices = {
        @Index(value = {"year", "month", "day"}, unique = true),
        @Index(value = {"shift_type"})
    }
)
public class ShiftSchedule {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "month")
    private int month;

    @ColumnInfo(name = "day")
    private int day;

    @ColumnInfo(name = "shift_type")
    @NonNull
    private String shiftType = "";

    @ColumnInfo(name = "start_time")
    private long startTime;

    @ColumnInfo(name = "end_time")
    private long endTime;

    @ColumnInfo(name = "date")
    @NonNull
    private String date = "";

    @ColumnInfo(name = "note")
    @NonNull
    private String note = "";

    public ShiftSchedule() {
    }

    @Ignore
    public ShiftSchedule(int year, int month, int day, @NonNull String shiftType) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.shiftType = shiftType;
        this.note = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @NonNull
    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(@NonNull String shiftType) {
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

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    @NonNull
    public String getNote() {
        return note;
    }

    public void setNote(@NonNull String note) {
        this.note = note != null ? note : "";
    }

    public String getTimeRange() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String startTimeStr = timeFormat.format(new Date(startTime));
        String endTimeStr = timeFormat.format(new Date(endTime));
        return String.format("%s - %s", startTimeStr, endTimeStr);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShiftSchedule that = (ShiftSchedule) o;
        return id == that.id &&
               year == that.year &&
               month == that.month &&
               day == that.day &&
               startTime == that.startTime &&
               endTime == that.endTime &&
               shiftType.equals(that.shiftType) &&
               date.equals(that.date) &&
               note.equals(that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, year, month, day, shiftType, startTime, endTime, date, note);
    }
} 