package com.shiftschedule.app;

import android.app.Application;
import com.shiftschedule.app.database.AppDatabase;

public class ShiftApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化数据库
        AppDatabase.getInstance(this);
    }
} 