package com.shiftschedule.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.shiftschedule.app.service.AlarmService;
import com.shiftschedule.app.service.NotificationService;
import com.shiftschedule.app.model.ShiftSchedule;
import com.shiftschedule.app.repository.ShiftRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationService notificationService = new NotificationService(context);
        
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case "SNOOZE_ALARM":
                    handleSnoozeAlarm(context, intent, notificationService);
                    break;
                case "DISMISS_ALARM":
                    notificationService.cancelNotification();
                    break;
                default:
                    handleShiftAlarm(context, intent, notificationService);
                    break;
            }
        } else {
            handleShiftAlarm(context, intent, notificationService);
        }
    }

    private void handleShiftAlarm(Context context, Intent intent, NotificationService notificationService) {
        int shiftId = intent.getIntExtra("shift_id", -1);
        if (shiftId == -1) return;

        ShiftRepository repository = new ShiftRepository(context);
        ShiftSchedule shift = repository.getShiftByIdSync(shiftId);
        if (shift == null) return;

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String startTime = timeFormat.format(new Date(shift.getStartTime()));
        
        String title = "班次提醒";
        String content = String.format("您的%s班次将在%s开始", shift.getShiftType(), startTime);
        boolean soundEnabled = intent.getBooleanExtra("sound_enabled", true);
        boolean vibrateEnabled = intent.getBooleanExtra("vibrate_enabled", true);

        notificationService.showNotification(title, content, soundEnabled, vibrateEnabled);
    }

    private void handleSnoozeAlarm(Context context, Intent intent, NotificationService notificationService) {
        // 取消当前通知
        notificationService.cancelNotification();

        // 5分钟后重新提醒
        AlarmService alarmService = new AlarmService(context);
        // TODO: 实现稍后提醒功能
    }
} 