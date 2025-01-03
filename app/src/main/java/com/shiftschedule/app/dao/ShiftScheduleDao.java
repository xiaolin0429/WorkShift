package com.shiftschedule.app.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.shiftschedule.app.model.ShiftSchedule;

import java.util.List;

@Dao
public interface ShiftScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ShiftSchedule shift);

    @Update
    void update(ShiftSchedule shift);

    @Delete
    void delete(ShiftSchedule shift);

    @Query("DELETE FROM shift_schedules")
    void deleteAll();

    @Query("DELETE FROM shift_schedules")
    void deleteAllShifts();

    @Query("SELECT * FROM shift_schedules WHERE id = :id")
    LiveData<ShiftSchedule> getShiftById(int id);

    @Query("SELECT * FROM shift_schedules WHERE id = :id")
    ShiftSchedule getShiftByIdSync(int id);

    @Query("SELECT * FROM shift_schedules WHERE date = :date")
    ShiftSchedule getShiftByDate(String date);

    @Query("SELECT * FROM shift_schedules WHERE date = :date")
    LiveData<List<ShiftSchedule>> getShiftsByDate(String date);

    @Query("SELECT * FROM shift_schedules WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    List<ShiftSchedule> getShiftsBetweenDates(String startDate, String endDate);

    @Query("SELECT * FROM shift_schedules WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    LiveData<List<ShiftSchedule>> getShiftsByDateRange(String startDate, String endDate);

    @Query("SELECT * FROM shift_schedules WHERE year = :year AND month = :month")
    List<ShiftSchedule> getShiftsByMonth(int year, int month);

    @Query("SELECT * FROM shift_schedules ORDER BY date ASC")
    LiveData<List<ShiftSchedule>> getAllShifts();

    @Query("SELECT COUNT(*) FROM shift_schedules WHERE year = :year AND month = :month")
    int getShiftCountForMonth(int year, int month);
} 