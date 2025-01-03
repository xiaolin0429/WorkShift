package com.shiftschedule.app.repository;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.LiveData;
import com.shiftschedule.app.dao.ShiftScheduleDao;
import com.shiftschedule.app.database.AppDatabase;
import com.shiftschedule.app.model.ShiftSchedule;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShiftRepository {
    private final ShiftScheduleDao shiftScheduleDao;
    private final ExecutorService executorService;

    public ShiftRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        shiftScheduleDao = database.shiftScheduleDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public ShiftRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
        shiftScheduleDao = database.shiftScheduleDao();
        executorService = Executors.newSingleThreadExecutor();
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

    public void deleteAllShifts() {
        executorService.execute(() -> {
            shiftScheduleDao.deleteAllShifts();
        });
    }
} 