package com.shiftschedule.app.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.shiftschedule.app.database.AppDatabase;
import com.shiftschedule.app.model.UserPreferences;
import com.shiftschedule.app.dao.UserPreferencesDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SettingsRepository {
    private final UserPreferencesDao userPreferencesDao;
    private final ExecutorService executorService;

    public SettingsRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        userPreferencesDao = database.userPreferencesDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<UserPreferences> getUserPreferences() {
        return userPreferencesDao.getUserPreferences();
    }

    public void updateUserPreferences(UserPreferences preferences) {
        executorService.execute(() -> {
            userPreferencesDao.update(preferences);
        });
    }

    public void cleanup() {
        executorService.shutdown();
    }
} 