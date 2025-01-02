package com.shiftschedule.app.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.shiftschedule.app.model.ShiftSchedule;
import com.shiftschedule.app.receiver.AlarmReceiver;

public class AlarmService {
    private final Context context;
    private final AlarmManager alarmManager;

    public AlarmService(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarm(ShiftSchedule shift) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("shift_id", shift.getId());
        
        // 使用FLAG_IMMUTABLE以符合Android 12及以上版本的要求
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                shift.getId(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // 设置闹钟时间（比班次开始时间提前30分钟）
        long alarmTime = shift.getStartTime() - 30 * 60 * 1000;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Android 12及以上版本，需要检查是否有SCHEDULE_EXACT_ALARM权限
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setAlarmClock(
                        new AlarmManager.AlarmClockInfo(alarmTime, pendingIntent),
                        pendingIntent
                );
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android 6.0-11，使用setExactAndAllowWhileIdle
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    alarmTime,
                    pendingIntent
            );
        } else {
            // Android 6.0以下版本，使用setExact
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    alarmTime,
                    pendingIntent
            );
        }
    }

    public void cancelAlarm(ShiftSchedule shift) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                shift.getId(),
                intent,
                PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE
        );
        
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }
} 