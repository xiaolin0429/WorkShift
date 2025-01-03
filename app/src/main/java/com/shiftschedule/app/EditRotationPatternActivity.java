package com.shiftschedule.app;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import com.shiftschedule.app.model.RotationPattern;
import com.shiftschedule.app.viewmodel.RotationViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;

public class EditRotationPatternActivity extends AppCompatActivity {
    private RotationViewModel viewModel;
    private TextInputEditText nameInput;
    private Slider groupCountSlider;
    private Slider cycleDaysSlider;
    private Slider durationSlider;
    private MaterialButton saveButton;
    
    private int patternId = -1;  // -1 表示新建模式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rotation_pattern);

        // 设置Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 初始化ViewModel
        viewModel = new ViewModelProvider(this).get(RotationViewModel.class);

        // 初始化视图
        initViews();
        setupListeners();

        // 获取传入的模式ID
        patternId = getIntent().getIntExtra("pattern_id", -1);
        if (patternId != -1) {
            // 编辑现有模式
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.edit_rotation_pattern);
            }
            loadPattern(patternId);
        } else {
            // 新建模式
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.new_rotation_pattern);
            }
        }
    }

    private void initViews() {
        nameInput = findViewById(R.id.input_pattern_name);
        groupCountSlider = findViewById(R.id.slider_group_count);
        cycleDaysSlider = findViewById(R.id.slider_cycle_days);
        durationSlider = findViewById(R.id.slider_duration);
        saveButton = findViewById(R.id.button_save);

        // 设置Slider的范围
        groupCountSlider.setValueFrom(2);
        groupCountSlider.setValueTo(6);
        groupCountSlider.setStepSize(1);

        cycleDaysSlider.setValueFrom(1);
        cycleDaysSlider.setValueTo(30);
        cycleDaysSlider.setStepSize(1);

        durationSlider.setValueFrom(30);
        durationSlider.setValueTo(360);
        durationSlider.setStepSize(30);
    }

    private void setupListeners() {
        saveButton.setOnClickListener(v -> savePattern());

        // 添加Slider的值变化监听器
        groupCountSlider.addOnChangeListener((slider, value, fromUser) -> 
            updateCycleDaysRange((int) value));
    }

    private void updateCycleDaysRange(int groupCount) {
        // 根据组数更新周期天数的范围
        cycleDaysSlider.setValueFrom(groupCount);
        cycleDaysSlider.setValueTo(groupCount * 7);
        
        // 如果当前值小于最小值，则设置为最小值
        if (cycleDaysSlider.getValue() < groupCount) {
            cycleDaysSlider.setValue(groupCount);
        }
    }

    private void loadPattern(int patternId) {
        viewModel.getPatternById(patternId).observe(this, pattern -> {
            if (pattern != null) {
                nameInput.setText(pattern.getName());
                groupCountSlider.setValue(pattern.getGroupCount());
                cycleDaysSlider.setValue(pattern.getCycleDays());
                durationSlider.setValue(pattern.getDurationDays());
            }
        });
    }

    private void savePattern() {
        String name = nameInput.getText() != null ? nameInput.getText().toString().trim() : "";
        if (name.isEmpty()) {
            nameInput.setError(getString(R.string.input_pattern_name));
            return;
        }

        int groupCount = (int) groupCountSlider.getValue();
        int cycleDays = (int) cycleDaysSlider.getValue();
        int durationDays = (int) durationSlider.getValue();

        RotationPattern pattern = new RotationPattern(name, groupCount, cycleDays, 0);
        pattern.setDurationDays(durationDays);

        if (patternId != -1) {
            pattern.setId(patternId);
            viewModel.update(pattern);
        } else {
            viewModel.insert(pattern);
        }

        Toast.makeText(this, getString(R.string.save_success), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 