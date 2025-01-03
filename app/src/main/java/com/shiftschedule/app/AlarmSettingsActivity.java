package com.shiftschedule.app;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.shiftschedule.app.model.AlarmSettings;
import com.shiftschedule.app.viewmodel.AlarmSettingsViewModel;
import com.shiftschedule.app.util.LogUtil;

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

        viewModel = new ViewModelProvider(this).get(AlarmSettingsViewModel.class);
        
        initViews();
        initListeners();
        observeSettings();
    }

    private void initViews() {
        seekBarTime = findViewById(R.id.seekbar_time);
        textTime = findViewById(R.id.text_time);
        checkNotification = findViewById(R.id.check_notification);
        checkSound = findViewById(R.id.check_sound);
        checkVibrate = findViewById(R.id.check_vibrate);

        seekBarTime.setMax(120); // 最大提前2小时
    }

    private void initListeners() {
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    updateTimeText(progress);
                    viewModel.updateReminderMinutes(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        checkNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.updateNotificationEnabled(isChecked);
            checkSound.setEnabled(isChecked);
            checkVibrate.setEnabled(isChecked);
        });

        checkSound.setOnCheckedChangeListener((buttonView, isChecked) -> 
            viewModel.updateSoundEnabled(isChecked));

        checkVibrate.setOnCheckedChangeListener((buttonView, isChecked) -> 
            viewModel.updateVibrationEnabled(isChecked));
    }

    private void observeSettings() {
        viewModel.getSettings().observe(this, this::updateUI);
    }

    private void updateUI(AlarmSettings settings) {
        LogUtil.d(TAG, "Settings updated: " + settings.getReminderMinutes() + " minutes");
        
        if (seekBarTime.getProgress() != settings.getReminderMinutes()) {
            seekBarTime.setProgress(settings.getReminderMinutes());
            updateTimeText(settings.getReminderMinutes());
        }

        checkNotification.setChecked(settings.isNotificationEnabled());
        checkSound.setChecked(settings.isSoundEnabled());
        checkVibrate.setChecked(settings.isVibrationEnabled());

        checkSound.setEnabled(settings.isNotificationEnabled());
        checkVibrate.setEnabled(settings.isNotificationEnabled());
    }

    private void updateTimeText(int minutes) {
        if (minutes >= 60) {
            int hours = minutes / 60;
            int mins = minutes % 60;
            textTime.setText(getString(R.string.time_format_hour_minute, hours, mins));
        } else {
            textTime.setText(getString(R.string.time_format_minute, minutes));
        }
    }
} 