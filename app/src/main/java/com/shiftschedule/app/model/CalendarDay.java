package com.shiftschedule.app.model;

import java.util.List;

public class CalendarDay {
    private String date;
    private int dayOfMonth;
    private String dayOfWeek;
    private List<ShiftSchedule> shifts;
    private boolean isCurrentMonth;
    private boolean isToday;

    public CalendarDay(String date, int dayOfMonth, String dayOfWeek, List<ShiftSchedule> shifts) {
        this.date = date;
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = dayOfWeek;
        this.shifts = shifts;
    }

    public CalendarDay(int year, int month, int day, boolean isCurrentMonth, String dayOfWeek, ShiftSchedule shift) {
        this.date = String.format("%d-%02d-%02d", year, month, day);
        this.dayOfMonth = day;
        this.dayOfWeek = dayOfWeek;
        this.isCurrentMonth = isCurrentMonth;
        if (shift != null) {
            this.shifts = List.of(shift);
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<ShiftSchedule> getShifts() {
        return shifts;
    }

    public void setShifts(List<ShiftSchedule> shifts) {
        this.shifts = shifts;
    }

    public boolean isCurrentMonth() {
        return isCurrentMonth;
    }

    public void setCurrentMonth(boolean currentMonth) {
        isCurrentMonth = currentMonth;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }
} 