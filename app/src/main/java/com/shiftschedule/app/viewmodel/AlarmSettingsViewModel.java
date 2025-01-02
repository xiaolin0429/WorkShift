package com.shiftschedule.app.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.shiftschedule.app.model.AlarmSettings;
import com.shiftschedule.app.repository.AlarmSettingsRepository;

public class AlarmSettingsViewModel extends AndroidViewModel {
    private AlarmSettingsRepository repository;
    private LiveData<AlarmSettings> settings;

    public AlarmSettingsViewModel(Application application) {
        super(application);
        repository = new AlarmSettingsRepository(application);
        settings = repository.getSettings();
    }

    public LiveData<AlarmSettings> getSettings() {
        return settings;
    }

    public void updateSettings(AlarmSettings settings) {
        repository.insert(settings);
    }

    public void saveSettings(int reminderMinutes, boolean notificationEnabled, 
                           boolean soundEnabled, boolean vibrateEnabled) {
        AlarmSettings settings = new AlarmSettings(
            reminderMinutes,
            notificationEnabled,
            soundEnabled,
            vibrateEnabled
        );
        updateSettings(settings);
    }
} 