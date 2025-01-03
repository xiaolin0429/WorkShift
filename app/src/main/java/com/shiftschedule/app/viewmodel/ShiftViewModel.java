package com.shiftschedule.app.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.shiftschedule.app.database.AppDatabase;
import com.shiftschedule.app.model.ShiftSchedule;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShiftViewModel extends AndroidViewModel {
    private final AppDatabase database;
    private final ExecutorService executor;
    private final MutableLiveData<ShiftSchedule> todayShift = new MutableLiveData<>();
    private final MutableLiveData<List<ShiftSchedule>> weekShifts = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<String> selectedDate = new MutableLiveData<>();
    private final MutableLiveData<ShiftSchedule> selectedShift = new MutableLiveData<>();

    public ShiftViewModel(Application application) {
        super(application);
        database = AppDatabase.getInstance(application);
        executor = Executors.newFixedThreadPool(2);
        refreshData();
    }

    public LiveData<ShiftSchedule> getTodayShift() {
        return todayShift;
    }

    public LiveData<List<ShiftSchedule>> getWeekShifts() {
        return weekShifts;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<String> getSelectedDate() {
        return selectedDate;
    }

    public LiveData<ShiftSchedule> getSelectedShift() {
        return selectedShift;
    }

    public void setSelectedDate(String date) {
        selectedDate.setValue(date);
        loadSelectedShift(date);
    }

    private void loadSelectedShift(String date) {
        isLoading.setValue(true);
        executor.execute(() -> {
            try {
                ShiftSchedule shift = database.shiftScheduleDao().getShiftByDate(date);
                selectedShift.postValue(shift);
                error.postValue(null);
            } catch (Exception e) {
                error.postValue("加载所选日期班次失败: " + e.getMessage());
            } finally {
                isLoading.postValue(false);
            }
        });
    }

    public LiveData<ShiftSchedule> getShiftById(int id) {
        MutableLiveData<ShiftSchedule> shift = new MutableLiveData<>();
        isLoading.setValue(true);
        executor.execute(() -> {
            try {
                ShiftSchedule result = database.shiftScheduleDao().getShiftByIdSync(id);
                shift.postValue(result);
                error.postValue(null);
            } catch (Exception e) {
                error.postValue("加载班次失败: " + e.getMessage());
            } finally {
                isLoading.postValue(false);
            }
        });
        return shift;
    }

    public LiveData<List<ShiftSchedule>> getShiftsByDateRange(String startDate, String endDate) {
        MutableLiveData<List<ShiftSchedule>> shifts = new MutableLiveData<>();
        isLoading.setValue(true);
        executor.execute(() -> {
            try {
                List<ShiftSchedule> result = database.shiftScheduleDao()
                        .getShiftsBetweenDates(startDate, endDate);
                shifts.postValue(result);
                error.postValue(null);
            } catch (Exception e) {
                error.postValue("加载班次失败: " + e.getMessage());
            } finally {
                isLoading.postValue(false);
            }
        });
        return shifts;
    }

    public void insert(ShiftSchedule shift) {
        executor.execute(() -> {
            try {
                database.shiftScheduleDao().insert(shift);
                error.postValue(null);
                refreshData();
            } catch (Exception e) {
                error.postValue("添加班次失败: " + e.getMessage());
            }
        });
    }

    public void update(ShiftSchedule shift) {
        executor.execute(() -> {
            try {
                database.shiftScheduleDao().update(shift);
                error.postValue(null);
                refreshData();
            } catch (Exception e) {
                error.postValue("更新班次失败: " + e.getMessage());
            }
        });
    }

    public void delete(ShiftSchedule shift) {
        executor.execute(() -> {
            try {
                database.shiftScheduleDao().delete(shift);
                error.postValue(null);
                refreshData();
            } catch (Exception e) {
                error.postValue("删除班次失败: " + e.getMessage());
            }
        });
    }

    public void refreshData() {
        loadTodayShift();
        loadWeekShifts();
    }

    private void loadTodayShift() {
        isLoading.setValue(true);
        executor.execute(() -> {
            try {
                Calendar calendar = Calendar.getInstance();
                String today = String.format("%d-%02d-%02d",
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH));

                ShiftSchedule shift = database.shiftScheduleDao().getShiftByDate(today);
                todayShift.postValue(shift);
                error.postValue(null);
            } catch (Exception e) {
                error.postValue("加载今日班次失败: " + e.getMessage());
            } finally {
                isLoading.postValue(false);
            }
        });
    }

    private void loadWeekShifts() {
        isLoading.setValue(true);
        executor.execute(() -> {
            try {
                Calendar calendar = Calendar.getInstance();
                // 设置为本周一
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                
                String startDate = String.format("%d-%02d-%02d",
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH));

                // 添加6天得到周日
                calendar.add(Calendar.DAY_OF_WEEK, 6);
                String endDate = String.format("%d-%02d-%02d",
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH));

                List<ShiftSchedule> shifts = database.shiftScheduleDao()
                        .getShiftsBetweenDates(startDate, endDate);
                weekShifts.postValue(shifts);
                error.postValue(null);
            } catch (Exception e) {
                error.postValue("加载本周班次失败: " + e.getMessage());
            } finally {
                isLoading.postValue(false);
            }
        });
    }

    public LiveData<List<ShiftSchedule>> getSelectedDateShifts(int year, int month) {
        MutableLiveData<List<ShiftSchedule>> shifts = new MutableLiveData<>();
        isLoading.setValue(true);
        executor.execute(() -> {
            try {
                List<ShiftSchedule> result = database.shiftScheduleDao().getShiftsByMonth(year, month);
                shifts.postValue(result);
                error.postValue(null);
            } catch (Exception e) {
                error.postValue("加载月度班次失败: " + e.getMessage());
            } finally {
                isLoading.postValue(false);
            }
        });
        return shifts;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executor.shutdown();
    }
} 