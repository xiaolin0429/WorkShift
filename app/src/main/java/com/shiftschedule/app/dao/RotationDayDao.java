package com.shiftschedule.app.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.shiftschedule.app.model.RotationDay;
import java.util.List;

@Dao
public interface RotationDayDao {
    @Insert
    long insert(RotationDay day);

    @Insert
    void insertAll(List<RotationDay> days);

    @Update
    void update(RotationDay day);

    @Delete
    void delete(RotationDay day);

    @Query("SELECT * FROM rotation_days WHERE patternId = :patternId ORDER BY dayIndex")
    LiveData<List<RotationDay>> getDaysByPattern(int patternId);

    @Query("DELETE FROM rotation_days WHERE patternId = :patternId")
    void deleteByPattern(int patternId);
} 