package com.shiftschedule.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import com.shiftschedule.app.model.AlarmSettings;
import com.shiftschedule.app.util.LogUtil;
import com.shiftschedule.app.viewmodel.AlarmSettingsViewModel;

public class AlarmSettingsActivity extends AppCompatActivity {
    private static final String TAG = "AlarmSettingsActivity";
    private AlarmSettingsViewModel viewModel;
    private SeekBar seekBarTime;
    private TextView textTime;
    private CheckBox checkNotification;
    private CheckBox checkSound;
    private CheckBox checkVibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_settings);

        // 设置Toolbar
        @SuppressLint("MissingInflatedId") Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        setupListeners();

        viewModel = new ViewModelProvider(this).get(AlarmSettingsViewModel.class);
        observeSettings();
    }

    private void initViews() {
        seekBarTime = findViewById(R.id.seek_bar_time);
        textTime = findViewById(R.id.text_time);
        checkNotification = findViewById(R.id.check_notification);
        checkSound = findViewById(R.id.check_sound);
        checkVibrate = findViewById(R.id.check_vibrate);

        // 设置SeekBar范围
        seekBarTime.setMax(120); // 最大2小时
    }

    private void setupListeners() {
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimeText(progress);
                if (fromUser) {
                    saveSettings();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        checkNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkSound.setEnabled(true);
                checkVibrate.setEnabled(true);
            } else {
                checkSound.setEnabled(false);
                checkVibrate.setEnabled(false);
            }
            saveSettings();
        });

        checkSound.setOnCheckedChangeListener((buttonView, isChecked) -> saveSettings());
        checkVibrate.setOnCheckedChangeListener((buttonView, isChecked) -> saveSettings());
    }

    private void observeSettings() {
        viewModel.getSettings().observe(this, settings -> {
            if (settings != null) {
                LogUtil.d(TAG, "Settings updated: " + settings.getReminderMinutes() + " minutes");
                if (seekBarTime.getProgress() != settings.getReminderMinutes()) {
                    seekBarTime.setProgress(settings.getReminderMinutes());
                    updateTimeText(settings.getReminderMinutes());
                }
                checkNotification.setChecked(settings.isNotificationEnabled());
                checkSound.setChecked(settings.isSoundEnabled());
                checkVibrate.setChecked(settings.isVibrateEnabled());

                // 更新复选框状态
                checkSound.setEnabled(settings.isNotificationEnabled());
                checkVibrate.setEnabled(settings.isNotificationEnabled());
            }
        });
    }

    private void updateTimeText(int minutes) {
        if (minutes >= 60) {
            int hours = minutes / 60;
            int mins = minutes % 60;
            if (mins > 0) {
                textTime.setText(String.format("%d小时%d分钟", hours, mins));
            } else {
                textTime.setText(String.format("%d小时", hours));
            }
        } else {
            textTime.setText(String.format("%d分钟", minutes));
        }
    }

    private void saveSettings() {
        LogUtil.i(TAG, "Saving settings");
        viewModel.saveSettings(
            seekBarTime.getProgress(),
            checkNotification.isChecked(),
            checkSound.isChecked(),
            checkVibrate.isChecked()
        );
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