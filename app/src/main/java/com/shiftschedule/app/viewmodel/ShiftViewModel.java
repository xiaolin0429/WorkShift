package com.shiftschedule.app.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.shiftschedule.app.model.ShiftSchedule;
import com.shiftschedule.app.repository.ShiftRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShiftViewModel extends AndroidViewModel {
    private final ShiftRepository repository;
    private final MutableLiveData<String> selectedDate = new MutableLiveData<>();
    private final LiveData<List<ShiftSchedule>> selectedDateShifts;
    private LiveData<List<ShiftSchedule>> allShifts;

    public ShiftViewModel(Application application) {
        super(application);
        repository = new ShiftRepository(application.getApplicationContext());
        allShifts = repository.getAllShifts();
        
        // 监听选中日期的班次
        selectedDateShifts = Transformations.switchMap(selectedDate, date ->
            repository.getShiftsByDate(date));
        
        // 设置默认日期为今天
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(new Date());
        selectedDate.setValue(today);
    }

    public void insert(ShiftSchedule schedule) {
        repository.insert(schedule);
    }

    public void update(ShiftSchedule schedule) {
        repository.update(schedule);
    }

    public void delete(ShiftSchedule schedule) {
        repository.delete(schedule);
    }

    public LiveData<List<ShiftSchedule>> getAllShifts() {
        return allShifts;
    }

    public LiveData<List<ShiftSchedule>> getShiftsByDateRange(String startDate, String endDate) {
        return repository.getShiftsByDateRange(startDate, endDate);
    }

    public LiveData<ShiftSchedule> getShiftById(int shiftId) {
        return repository.getShiftById(shiftId);
    }

    public void setSelectedDate(String date) {
        selectedDate.setValue(date);
    }

    public LiveData<List<ShiftSchedule>> getSelectedDateShifts() {
        return selectedDateShifts;
    }

    public LiveData<String> getSelectedDate() {
        return selectedDate;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.cleanup();
    }
} 