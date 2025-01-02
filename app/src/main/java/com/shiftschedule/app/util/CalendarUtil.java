package com.shiftschedule.app.util;

import com.shiftschedule.app.model.CalendarDay;
import com.shiftschedule.app.model.ShiftSchedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarUtil {
    public static List<CalendarDay> getCalendarDays(Calendar calendar) {
        List<CalendarDay> days = new ArrayList<>();
        
        // 设置为当月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        
        // 获取当月第一天是星期几
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        
        // 添加上月剩余天数
        calendar.add(Calendar.DAY_OF_MONTH, -firstDayOfWeek);
        for (int i = 0; i < firstDayOfWeek; i++) {
            String date = String.format(Locale.US, "%d-%02d-%02d",
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH));
            String lunarDay = LunarCalendarUtil.getLunarDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            days.add(new CalendarDay(date, calendar.get(Calendar.DAY_OF_MONTH), lunarDay, new ArrayList<>()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        return days;
    }
} 