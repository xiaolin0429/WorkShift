package com.shiftschedule.app.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.shiftschedule.app.model.AlarmSettings;

@Dao
public interface AlarmSettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AlarmSettings settings);

    @Update
    void update(AlarmSettings settings);

    @Query("SELECT * FROM alarm_settings WHERE id = 1")
    LiveData<AlarmSettings> getSettings();

    @Query("SELECT * FROM alarm_settings WHERE id = 1")
    AlarmSettings getSettingsSync();
} 