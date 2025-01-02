package com.shiftschedule.app.util;

import java.util.HashMap;
import java.util.Map;

public class HolidayUtil {
    private static final Map<String, String> HOLIDAY_MAP = new HashMap<>();
    
    static {
        // 2024年节假日数据
        // 元旦
        HOLIDAY_MAP.put("2024-01-01", "元旦");
        
        // 春节
        HOLIDAY_MAP.put("2024-02-10", "春节");
        HOLIDAY_MAP.put("2024-02-11", "初二");
        HOLIDAY_MAP.put("2024-02-12", "初三");
        HOLIDAY_MAP.put("2024-02-13", "初四");
        HOLIDAY_MAP.put("2024-02-14", "初五");
        HOLIDAY_MAP.put("2024-02-15", "初六");
        HOLIDAY_MAP.put("2024-02-16", "初七");
        
        // 清明节
        HOLIDAY_MAP.put("2024-04-04", "清明");
        HOLIDAY_MAP.put("2024-04-05", "清明");
        HOLIDAY_MAP.put("2024-04-06", "清明");
        
        // 劳动节
        HOLIDAY_MAP.put("2024-05-01", "劳动节");
        HOLIDAY_MAP.put("2024-05-02", "劳动节");
        HOLIDAY_MAP.put("2024-05-03", "劳动节");
        HOLIDAY_MAP.put("2024-05-04", "劳动节");
        HOLIDAY_MAP.put("2024-05-05", "劳动节");
        
        // 端午节
        HOLIDAY_MAP.put("2024-06-10", "端午");
        
        // 中秋节
        HOLIDAY_MAP.put("2024-09-17", "中秋");
        
        // 国庆节
        HOLIDAY_MAP.put("2024-10-01", "国庆");
        HOLIDAY_MAP.put("2024-10-02", "国庆");
        HOLIDAY_MAP.put("2024-10-03", "国庆");
        HOLIDAY_MAP.put("2024-10-04", "国庆");
        HOLIDAY_MAP.put("2024-10-05", "国庆");
        HOLIDAY_MAP.put("2024-10-06", "国庆");
        HOLIDAY_MAP.put("2024-10-07", "国庆");
    }
    
    public static boolean isHoliday(String date) {
        return HOLIDAY_MAP.containsKey(date);
    }
    
    public static String getHolidayName(String date) {
        return HOLIDAY_MAP.get(date);
    }
} 