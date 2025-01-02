package com.shiftschedule.app.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.shiftschedule.app.model.RotationPattern;
import com.shiftschedule.app.repository.ShiftRepository;
import java.util.List;

public class RotationViewModel extends AndroidViewModel {
    private final ShiftRepository repository;

    public RotationViewModel(Application application) {
        super(application);
        repository = new ShiftRepository(application);
    }

    public LiveData<List<RotationPattern>> getAllPatterns() {
        return repository.getAllPatterns();
    }

    public LiveData<RotationPattern> getPatternById(int id) {
        return repository.getPatternById(id);
    }

    public void insert(RotationPattern pattern) {
        repository.insert(pattern);
    }

    public void update(RotationPattern pattern) {
        repository.update(pattern);
    }

    public void delete(RotationPattern pattern) {
        repository.delete(pattern);
    }

    public void activatePattern(RotationPattern pattern) {
        // 先停用所有模式
        repository.deactivateAllPatterns();
        
        // 激活选中的模式
        pattern.setActive(true);
        repository.update(pattern);
    }
} 