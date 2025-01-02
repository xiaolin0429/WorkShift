package com.shiftschedule.app.worker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.shiftschedule.app.R;

public class ShiftReminderWorker extends Worker {
    public static final String NOTIFICATION_CHANNEL_ID = "SHIFT_REMINDER";
    public static final String KEY_SHIFT_TITLE = "shift_title";
    public static final String KEY_SHIFT_MESSAGE = "shift_message";

    public ShiftReminderWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        String title = getInputData().getString(KEY_SHIFT_TITLE);
        String message = getInputData().getString(KEY_SHIFT_MESSAGE);
        
        createNotificationChannel();
        showNotification(title, message);
        
        return Result.success();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "班次提醒",
                NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("用于显示班次开始前的提醒");
            
            NotificationManager notificationManager = getApplicationContext()
                .getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
            getApplicationContext(), NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) 
            getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
} 