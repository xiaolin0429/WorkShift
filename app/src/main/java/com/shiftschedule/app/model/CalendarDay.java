package com.shiftschedule.app.model;

import java.util.List;

public class CalendarDay {
    private String date;
    private int dayOfMonth;
    private String lunarDay;
    private List<ShiftSchedule> shifts;

    public CalendarDay(String date, int dayOfMonth, String lunarDay, List<ShiftSchedule> shifts) {
        this.date = date;
        this.dayOfMonth = dayOfMonth;
        this.lunarDay = lunarDay;
        this.shifts = shifts;
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

    public String getLunarDay() {
        return lunarDay;
    }

    public void setLunarDay(String lunarDay) {
        this.lunarDay = lunarDay;
    }

    public List<ShiftSchedule> getShifts() {
        return shifts;
    }

    public void setShifts(List<ShiftSchedule> shifts) {
        this.shifts = shifts;
    }
} 