package com.shiftschedule.app;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.Snackbar;
import com.shiftschedule.app.util.LogUtil;
import com.shiftschedule.app.viewmodel.SettingsViewModel;
import com.shiftschedule.app.model.UserPreferences;
import com.shiftschedule.app.model.AlarmSettings;
import com.shiftschedule.app.repository.AlarmSettingsRepository;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";

    private View layoutAlarmSettings;
    private View layoutExportLog;
    private TextView textAlarmSummary;
    private SettingsViewModel settingsViewModel;
    private AlarmSettingsRepository alarmSettingsRepository;

    // 创建文件选择器的结果处理器
    private final ActivityResultLauncher<Intent> createFileLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri uri = result.getData().getData();
                if (uri != null) {
                    if (LogUtil.exportLogFile(this, uri)) {
                        showMessage("日志导出成功");
                        LogUtil.i(TAG, "Log exported successfully to: " + uri);
                    } else {
                        showMessage("日志导出失败");
                        LogUtil.e(TAG, "Failed to export log to: " + uri);
                    }
                }
            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // 设置Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        alarmSettingsRepository = new AlarmSettingsRepository(this);
        
        initViews();
        setupListeners();
        observeSettings();
    }

    private void initViews() {
        layoutAlarmSettings = findViewById(R.id.layout_alarm_settings);
        layoutExportLog = findViewById(R.id.layout_export_log);
        textAlarmSummary = findViewById(R.id.text_alarm_summary);
    }

    private void setupListeners() {
        layoutAlarmSettings.setOnClickListener(v -> {
            Intent intent = new Intent(this, AlarmSettingsActivity.class);
            startActivity(intent);
        });

        layoutExportLog.setOnClickListener(v -> exportLogs());
    }

    private void observeSettings() {
        alarmSettingsRepository.getSettings().observe(this, settings -> {
            if (settings != null) {
                updateAlarmSettingsSummary(settings);
            }
        });

        settingsViewModel.getUserPreferences().observe(this, preferences -> {
            if (preferences != null) {
                updateUIWithPreferences(preferences);
            }
        });
    }

    private void updateAlarmSettingsSummary(AlarmSettings settings) {
        StringBuilder summary = new StringBuilder();
        summary.append("提前");
        
        if (settings.getReminderMinutes() >= 60) {
            int hours = settings.getReminderMinutes() / 60;
            summary.append(hours).append("小时");
            int minutes = settings.getReminderMinutes() % 60;
            if (minutes > 0) {
                summary.append(minutes).append("分钟");
            }
        } else {
            summary.append(settings.getReminderMinutes()).append("分钟");
        }
        
        summary.append("提醒");

        if (!settings.isNotificationEnabled()) {
            summary.append("（已关闭）");
        }

        textAlarmSummary.setText(summary.toString());
    }

    private void updateUIWithPreferences(UserPreferences preferences) {
        // 根据用户偏好设置更新UI
        // TODO: 实现用户偏好设置的UI更新
        // 例如：通知设置、声音设置等
    }

    private void exportLogs() {
        LogUtil.i(TAG, "Starting log export");
        List<File> logFiles = LogUtil.getAllLogFiles(this);
        if (logFiles.isEmpty()) {
            showMessage("没有可导出的日志文件");
            return;
        }

        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        String fileName = "shift_schedule_logs_" + 
                new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                        .format(new Date()) + ".txt";
        intent.putExtra(Intent.EXTRA_TITLE, fileName);

        try {
            createFileLauncher.launch(intent);
        } catch (ActivityNotFoundException e) {
            LogUtil.e(TAG, "No activity can handle file creation", e);
            showMessage("无法创建文件，请检查系统权限");
        }
    }

    private void showMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alarmSettingsRepository != null) {
            alarmSettingsRepository.cleanup();
        }
    }
} 