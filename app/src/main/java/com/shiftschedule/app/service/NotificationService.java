package com.shiftschedule.app.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.shiftschedule.app.MainActivity;
import com.shiftschedule.app.R;
import com.shiftschedule.app.receiver.AlarmReceiver;
import android.app.Notification;

public class NotificationService {
    private static final String CHANNEL_ID = "shift_reminder";
    public static final int NOTIFICATION_ID = 1;
    private final Context context;
    private final NotificationManager notificationManager;

    public NotificationService(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    public void showNotification(String title, String content, boolean soundEnabled, boolean vibrateEnabled) {
        // 创建通知点击意图
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // 创建大图标
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification_large);

        // 构建通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(largeIcon)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(content)
                        .setBigContentTitle(title)
                        .setSummaryText("班次提醒"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, R.color.notification_color))
                .setColorized(true);

        // 设置声音
        if (soundEnabled) {
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmSound == null) {
                alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
            builder.setSound(alarmSound);
        }

        // 设置震动
        if (vibrateEnabled) {
            long[] vibrationPattern = {0, 500, 200, 500}; // 震动模式：等待、震动、等待、震动
            builder.setVibrate(vibrationPattern);
        }

        // 添加操作按钮
        Intent snoozeIntent = new Intent(context, AlarmReceiver.class);
        snoozeIntent.setAction("SNOOZE_ALARM");
        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(context, 0,
                snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        builder.addAction(R.drawable.ic_snooze, "稍后提醒", snoozePendingIntent);

        Intent dismissIntent = new Intent(context, AlarmReceiver.class);
        dismissIntent.setAction("DISMISS_ALARM");
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(context, 0,
                dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        builder.addAction(R.drawable.ic_dismiss, "关闭", dismissPendingIntent);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "班次提醒",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("提醒即将开始的班次");
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setLightColor(ContextCompat.getColor(context, R.color.notification_color));
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();
            channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM), audioAttributes);
            
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void cancelNotification() {
        notificationManager.cancel(NOTIFICATION_ID);
    }
} 