package com.shiftschedule.app.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.shiftschedule.app.model.UserPreferences;

@Dao
public interface UserPreferencesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserPreferences preferences);

    @Update
    void update(UserPreferences preferences);

    @Query("SELECT * FROM user_preferences WHERE id = 1")
    LiveData<UserPreferences> getUserPreferences();

    @Query("SELECT * FROM user_preferences WHERE id = 1")
    UserPreferences getUserPreferencesSync();
} 