package com.shiftschedule.app.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.shiftschedule.app.model.UserPreferences;

@Dao
public interface UserPreferencesDao {
    @Query("SELECT * FROM user_preferences LIMIT 1")
    LiveData<UserPreferences> getUserPreferences();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserPreferences preferences);

    @Update
    void update(UserPreferences preferences);

    @Delete
    void delete(UserPreferences preferences);
} 