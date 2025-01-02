package com.shiftschedule.app.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.shiftschedule.app.database.AppDatabase;
import com.shiftschedule.app.model.AlarmSettings;
import com.shiftschedule.app.dao.AlarmSettingsDao;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AlarmSettingsRepository {
    private final AlarmSettingsDao alarmSettingsDao;
    private final ExecutorService executorService;

    public AlarmSettingsRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        alarmSettingsDao = database.alarmSettingsDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<AlarmSettings> getSettings() {
        return alarmSettingsDao.getSettings();
    }

    public AlarmSettings getSettingsSync() {
        return alarmSettingsDao.getSettingsSync();
    }

    public void insert(AlarmSettings settings) {
        executorService.execute(() -> {
            alarmSettingsDao.insert(settings);
        });
    }

    public void update(AlarmSettings settings) {
        executorService.execute(() -> {
            alarmSettingsDao.update(settings);
        });
    }

    public void cleanup() {
        executorService.shutdown();
    }
} 