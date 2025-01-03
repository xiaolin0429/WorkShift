package com.shiftschedule.app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import com.shiftschedule.app.model.ShiftSchedule;
import com.shiftschedule.app.viewmodel.ShiftViewModel;
import com.shiftschedule.app.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditShiftActivity extends AppCompatActivity {
    private static final String TAG = "EditShiftActivity";
    private ShiftViewModel shiftViewModel;
    private TextInputEditText editDate;
    private AutoCompleteTextView spinnerShiftType;
    private TextInputEditText editStartTime;
    private TextInputEditText editEndTime;
    private TextInputEditText editNote;
    private Calendar calendar;
    private ShiftSchedule currentShift;
    private int shiftId = -1;

    private static final String[] SHIFT_TYPES = {"早班", "中班", "晚班"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shift);

        // 设置Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.edit_shift);
        }

        // 初始化视图
        initViews();

        // 初始化ViewModel
        shiftViewModel = new ViewModelProvider(this).get(ShiftViewModel.class);

        // 获取传入的班次ID
        shiftId = getIntent().getIntExtra("shift_id", -1);
        if (shiftId != -1) {
            // 加载现有班次数据
            loadShiftData();
        } else {
            // 新建班次，设置默认日期为今天
            calendar = Calendar.getInstance();
            updateDateDisplay();
        }
    }

    private void initViews() {
        editDate = findViewById(R.id.edit_date);
        spinnerShiftType = findViewById(R.id.spinner_shift_type);
        editStartTime = findViewById(R.id.edit_start_time);
        editEndTime = findViewById(R.id.edit_end_time);
        editNote = findViewById(R.id.edit_note);

        // 设置班次类型下拉列表
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, SHIFT_TYPES);
        spinnerShiftType.setAdapter(adapter);

        // 设置日期选择器
        editDate.setOnClickListener(v -> showDatePicker());

        // 设置时间选择器
        editStartTime.setOnClickListener(v -> showTimePicker(true));
        editEndTime.setOnClickListener(v -> showTimePicker(false));

        // 设置保存按钮
        findViewById(R.id.button_save).setOnClickListener(v -> saveShift());

        // 设置删除按钮
        findViewById(R.id.button_delete).setOnClickListener(v -> deleteShift());
    }

    private void loadShiftData() {
        shiftViewModel.getShiftById(shiftId).observe(this, shift -> {
            if (shift != null) {
                currentShift = shift;
                calendar = Calendar.getInstance();
                calendar.setTimeInMillis(shift.getStartTime());
                
                updateDateDisplay();
                spinnerShiftType.setText(shift.getShiftType(), false);
                updateTimeDisplay(editStartTime, shift.getStartTime());
                updateTimeDisplay(editEndTime, shift.getEndTime());
                if (shift.getNote() != null) {
                    editNote.setText(shift.getNote());
                }
            }
        });
    }

    private void showDatePicker() {
        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    updateDateDisplay();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void showTimePicker(boolean isStartTime) {
        TimePickerDialog dialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(calendar.getTimeInMillis());
                    cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    cal.set(Calendar.MINUTE, minute);
                    
                    if (isStartTime) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        updateTimeDisplay(editStartTime, cal.getTimeInMillis());
                    } else {
                        updateTimeDisplay(editEndTime, cal.getTimeInMillis());
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        dialog.show();
    }

    private void updateDateDisplay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        editDate.setText(dateFormat.format(calendar.getTime()));
    }

    private void updateTimeDisplay(TextInputEditText view, long timeInMillis) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        view.setText(timeFormat.format(new Date(timeInMillis)));
    }

    private void saveShift() {
        CharSequence dateText = editDate.getText();
        CharSequence shiftTypeText = spinnerShiftType.getText();
        CharSequence startTimeText = editStartTime.getText();
        CharSequence endTimeText = editEndTime.getText();
        CharSequence noteText = editNote.getText();

        String date = dateText != null ? dateText.toString() : "";
        String shiftType = shiftTypeText != null ? shiftTypeText.toString() : "";
        String startTimeStr = startTimeText != null ? startTimeText.toString() : "";
        String endTimeStr = endTimeText != null ? endTimeText.toString() : "";
        String note = noteText != null ? noteText.toString() : "";

        // 验证输入
        if (date.isEmpty() || shiftType.isEmpty() || 
            startTimeStr.isEmpty() || endTimeStr.isEmpty()) {
            // 显示错误提示
            return;
        }

        // 创建或更新班次
        ShiftSchedule shift = currentShift != null ? currentShift : new ShiftSchedule();
        shift.setDate(date);
        shift.setShiftType(shiftType);
        
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(timeFormat.parse(startTimeStr));
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(timeFormat.parse(endTimeStr));
            
            // 设置日期部分
            startCal.set(calendar.get(Calendar.YEAR), 
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
            endCal.set(calendar.get(Calendar.YEAR),
                      calendar.get(Calendar.MONTH),
                      calendar.get(Calendar.DAY_OF_MONTH));
            
            // 如果结束时间小于开始时间，说明跨天了，需要加一天
            if (endCal.before(startCal)) {
                endCal.add(Calendar.DAY_OF_MONTH, 1);
            }
            
            shift.setStartTime(startCal.getTimeInMillis());
            shift.setEndTime(endCal.getTimeInMillis());
            shift.setNote(note);
            
            if (currentShift != null) {
                shiftViewModel.update(shift);
            } else {
                shiftViewModel.insert(shift);
            }
            
            finish();
        } catch (Exception e) {
            LogUtil.e(TAG, "Error saving shift", e);
            // 显示错误提示
        }
    }

    private void deleteShift() {
        if (currentShift != null) {
            shiftViewModel.delete(currentShift);
        }
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