package com.shiftschedule.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import com.shiftschedule.app.R;
import com.shiftschedule.app.AlarmSettingsActivity;
import com.shiftschedule.app.RotationSettingsActivity;

public class SettingsFragment extends PreferenceFragmentCompat {
    private static final String TAG = "SettingsFragment";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        // 设置轮班设置的点击事件
        findPreference("rotation_settings").setOnPreferenceClickListener(preference -> {
            startActivity(new Intent(requireContext(), RotationSettingsActivity.class));
            return true;
        });

        // 设置闹钟设置的点击事件
        findPreference("alarm_settings").setOnPreferenceClickListener(preference -> {
            startActivity(new Intent(requireContext(), AlarmSettingsActivity.class));
            return true;
        });
    }
} 