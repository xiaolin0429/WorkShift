package com.shiftschedule.app.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.shiftschedule.app.database.AppDatabase;
import com.shiftschedule.app.model.ShiftSchedule;
import com.shiftschedule.app.model.RotationPattern;
import com.shiftschedule.app.dao.ShiftScheduleDao;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShiftRepository {
    private final ShiftScheduleDao shiftScheduleDao;
    private final ExecutorService executorService;

    public ShiftRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        shiftScheduleDao = database.shiftScheduleDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    // 轮班模式相关操作
    public LiveData<List<RotationPattern>> getAllPatterns() {
        return shiftScheduleDao.getAllPatterns();
    }

    public LiveData<RotationPattern> getPatternById(int id) {
        return shiftScheduleDao.getPatternById(id);
    }

    public void insert(RotationPattern pattern) {
        executorService.execute(() -> {
            shiftScheduleDao.insert(pattern);
        });
    }

    public void update(RotationPattern pattern) {
        executorService.execute(() -> {
            shiftScheduleDao.update(pattern);
        });
    }

    public void delete(RotationPattern pattern) {
        executorService.execute(() -> {
            shiftScheduleDao.delete(pattern);
        });
    }

    public void deactivateAllPatterns() {
        executorService.execute(() -> {
            List<RotationPattern> patterns = shiftScheduleDao.getAllPatternsSync();
            for (RotationPattern pattern : patterns) {
                if (pattern.isActive()) {
                    pattern.setActive(false);
                    shiftScheduleDao.update(pattern);
                }
            }
        });
    }

    // 排班相关操作
    public LiveData<List<ShiftSchedule>> getAllShifts() {
        return shiftScheduleDao.getAllShifts();
    }

    public LiveData<List<ShiftSchedule>> getShiftsByDate(String date) {
        return shiftScheduleDao.getShiftsByDate(date);
    }

    public LiveData<List<ShiftSchedule>> getShiftsByDateRange(String startDate, String endDate) {
        return shiftScheduleDao.getShiftsByDateRange(startDate, endDate);
    }

    public LiveData<ShiftSchedule> getShiftById(int shiftId) {
        return shiftScheduleDao.getShiftById(shiftId);
    }

    public ShiftSchedule getShiftByIdSync(int shiftId) {
        return shiftScheduleDao.getShiftByIdSync(shiftId);
    }

    public void insert(ShiftSchedule shift) {
        executorService.execute(() -> {
            shiftScheduleDao.insert(shift);
        });
    }

    public void update(ShiftSchedule shift) {
        executorService.execute(() -> {
            shiftScheduleDao.update(shift);
        });
    }

    public void delete(ShiftSchedule shift) {
        executorService.execute(() -> {
            shiftScheduleDao.delete(shift);
        });
    }

    public void cleanup() {
        executorService.shutdown();
    }
} 