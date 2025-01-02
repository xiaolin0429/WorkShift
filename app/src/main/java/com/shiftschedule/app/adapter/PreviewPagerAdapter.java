package com.shiftschedule.app.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.shiftschedule.app.PreviewPatternActivity.PreviewDay;
import com.shiftschedule.app.fragment.ListPreviewFragment;
import com.shiftschedule.app.fragment.CalendarPreviewFragment;
import java.util.List;

public class PreviewPagerAdapter extends FragmentStateAdapter {
    private ListPreviewFragment listFragment;
    private CalendarPreviewFragment calendarFragment;
    private List<PreviewDay> previewDays;

    public PreviewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<PreviewDay> previewDays) {
        super(fragmentActivity);
        this.previewDays = previewDays;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            if (listFragment == null) {
                listFragment = ListPreviewFragment.newInstance(previewDays);
            }
            return listFragment;
        } else {
            if (calendarFragment == null) {
                calendarFragment = CalendarPreviewFragment.newInstance(previewDays);
            }
            return calendarFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public void updatePreviewDays(List<PreviewDay> previewDays) {
        this.previewDays = previewDays;
        if (listFragment != null) {
            listFragment.updatePreviewDays(previewDays);
        }
        if (calendarFragment != null) {
            calendarFragment.updatePreviewDays(previewDays);
        }
    }
} 