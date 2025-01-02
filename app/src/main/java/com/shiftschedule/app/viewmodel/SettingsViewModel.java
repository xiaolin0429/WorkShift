package com.shiftschedule.app.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.shiftschedule.app.model.UserPreferences;
import com.shiftschedule.app.repository.SettingsRepository;

public class SettingsViewModel extends AndroidViewModel {
    private final SettingsRepository repository;

    public SettingsViewModel(Application application) {
        super(application);
        repository = new SettingsRepository(application);
    }

    public LiveData<UserPreferences> getUserPreferences() {
        return repository.getUserPreferences();
    }

    public void updateUserPreferences(UserPreferences preferences) {
        repository.updateUserPreferences(preferences);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.cleanup();
    }
} 