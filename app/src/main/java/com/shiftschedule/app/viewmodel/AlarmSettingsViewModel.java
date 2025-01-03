package com.shiftschedule.app.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.shiftschedule.app.model.AlarmSettings;
import com.shiftschedule.app.repository.AlarmSettingsRepository;

public class AlarmSettingsViewModel extends AndroidViewModel {
    private final AlarmSettingsRepository repository;
    private final LiveData<AlarmSettings> settings;

    public AlarmSettingsViewModel(Application application) {
        super(application);
        repository = new AlarmSettingsRepository(application);
        settings = repository.getSettings();
        
        // 如果没有设置，创建默认设置
        if (settings.getValue() == null) {
            AlarmSettings defaultSettings = new AlarmSettings();
            defaultSettings.setReminderMinutes(30);
            defaultSettings.setNotificationEnabled(true);
            defaultSettings.setSoundEnabled(true);
            defaultSettings.setVibrationEnabled(true);
            repository.insert(defaultSettings);
        }
    }

    public LiveData<AlarmSettings> getSettings() {
        return settings;
    }

    public void updateReminderMinutes(int minutes) {
        AlarmSettings current = settings.getValue();
        if (current != null) {
            current.setReminderMinutes(minutes);
            repository.update(current);
        }
    }

    public void updateNotificationEnabled(boolean enabled) {
        AlarmSettings current = settings.getValue();
        if (current != null) {
            current.setNotificationEnabled(enabled);
            repository.update(current);
        }
    }

    public void updateSoundEnabled(boolean enabled) {
        AlarmSettings current = settings.getValue();
        if (current != null) {
            current.setSoundEnabled(enabled);
            repository.update(current);
        }
    }

    public void updateVibrationEnabled(boolean enabled) {
        AlarmSettings current = settings.getValue();
        if (current != null) {
            current.setVibrationEnabled(enabled);
            repository.update(current);
        }
    }
} 