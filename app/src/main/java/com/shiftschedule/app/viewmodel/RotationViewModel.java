package com.shiftschedule.app.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.shiftschedule.app.model.RotationPattern;
import com.shiftschedule.app.repository.RotationRepository;
import java.util.List;

public class RotationViewModel extends AndroidViewModel {
    private final RotationRepository repository;

    public RotationViewModel(Application application) {
        super(application);
        repository = new RotationRepository(application);
    }

    public LiveData<List<RotationPattern>> getAllPatterns() {
        return repository.getAllPatterns();
    }

    public LiveData<RotationPattern> getPatternById(int id) {
        return repository.getPatternById(id);
    }

    public LiveData<RotationPattern> getActivePattern() {
        return repository.getActivePattern();
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

    public void deactivateAllPatterns() {
        repository.deactivateAllPatterns();
    }

    public void activatePattern(RotationPattern pattern) {
        deactivateAllPatterns();
        pattern.setActive(true);
        update(pattern);
    }
} 