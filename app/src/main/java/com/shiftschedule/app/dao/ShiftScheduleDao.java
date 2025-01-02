package com.shiftschedule.app.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.shiftschedule.app.model.ShiftSchedule;
import com.shiftschedule.app.model.RotationPattern;

import java.util.List;

@Dao
public interface ShiftScheduleDao {
    @Query("SELECT * FROM rotation_patterns ORDER BY id DESC")
    LiveData<List<RotationPattern>> getAllPatterns();

    @Query("SELECT * FROM rotation_patterns ORDER BY id DESC")
    List<RotationPattern> getAllPatternsSync();

    @Query("SELECT * FROM rotation_patterns WHERE id = :id")
    LiveData<RotationPattern> getPatternById(int id);

    @Insert
    void insert(RotationPattern pattern);

    @Update
    void update(RotationPattern pattern);

    @Delete
    void delete(RotationPattern pattern);

    @Query("SELECT * FROM shift_schedules ORDER BY date DESC")
    LiveData<List<ShiftSchedule>> getAllShifts();

    @Query("SELECT * FROM shift_schedules WHERE date = :date")
    LiveData<List<ShiftSchedule>> getShiftsByDate(String date);

    @Query("SELECT * FROM shift_schedules WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    LiveData<List<ShiftSchedule>> getShiftsByDateRange(String startDate, String endDate);

    @Query("SELECT * FROM shift_schedules WHERE id = :shiftId")
    LiveData<ShiftSchedule> getShiftById(int shiftId);

    @Query("SELECT * FROM shift_schedules WHERE id = :shiftId")
    ShiftSchedule getShiftByIdSync(int shiftId);

    @Insert
    void insert(ShiftSchedule shift);

    @Update
    void update(ShiftSchedule shift);

    @Delete
    void delete(ShiftSchedule shift);
} 