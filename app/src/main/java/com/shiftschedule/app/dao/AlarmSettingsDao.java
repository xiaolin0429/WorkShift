package com.shiftschedule.app.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.shiftschedule.app.model.AlarmSettings;

@Dao
public interface AlarmSettingsDao {
    @Query("SELECT * FROM alarm_settings LIMIT 1")
    LiveData<AlarmSettings> getSettings();

    @Query("SELECT * FROM alarm_settings LIMIT 1")
    AlarmSettings getSettingsSync();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AlarmSettings settings);

    @Update
    void update(AlarmSettings settings);

    @Delete
    void delete(AlarmSettings settings);
} 