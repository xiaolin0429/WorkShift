package com.shiftschedule.app.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.shiftschedule.app.R;
import com.shiftschedule.app.PreviewPatternActivity.PreviewDay;
import com.shiftschedule.app.view.GroupDayDecorator;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CalendarPreviewFragment extends Fragment {
    private CalendarView calendarView;
    private TextView selectedDayText;
    private Map<Long, PreviewDay> dayMap = new HashMap<>();
    private List<PreviewDay> previewDays;

    public static CalendarPreviewFragment newInstance(List<PreviewDay> previewDays) {
        CalendarPreviewFragment fragment = new CalendarPreviewFragment();
        fragment.previewDays = previewDays;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar_preview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarView = view.findViewById(R.id.calendar_view);
        selectedDayText = view.findViewById(R.id.text_selected_day);

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            long timeInMillis = calendar.getTimeInMillis();
            PreviewDay previewDay = dayMap.get(timeInMillis);
            if (previewDay != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 E", Locale.CHINESE);
                String dateStr = dateFormat.format(calendar.getTime());
                selectedDayText.setText(String.format("%s：%s", dateStr, previewDay.getGroupInfo()));
                selectedDayText.setTextColor(requireContext().getResources().getColor(
                        GroupDayDecorator.getGroupColor(previewDay.getGroupIndex())));
            } else {
                selectedDayText.setText("该日期不在预览范围内");
                selectedDayText.setTextColor(requireContext().getResources().getColor(android.R.color.darker_gray));
            }
        });

        if (previewDays != null) {
            updatePreviewDays(previewDays);
        }
    }

    public void updatePreviewDays(List<PreviewDay> previewDays) {
        this.previewDays = previewDays;
        dayMap.clear();

        for (PreviewDay day : previewDays) {
            dayMap.put(day.getDateMillis(), day);
        }

        // 触发当前日期的选择
        long currentTimeMillis = Calendar.getInstance().getTimeInMillis();
        PreviewDay currentDay = dayMap.get(currentTimeMillis);
        if (currentDay != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 E", Locale.CHINESE);
            String dateStr = dateFormat.format(new Date());
            selectedDayText.setText(String.format("%s：%s", dateStr, currentDay.getGroupInfo()));
            selectedDayText.setTextColor(requireContext().getResources().getColor(
                    GroupDayDecorator.getGroupColor(currentDay.getGroupIndex())));
        }

        // 更新日历视图
        updateCalendarDecorations();
    }

    private void updateCalendarDecorations() {
        // 获取日历的背景
        Drawable background = calendarView.getBackground();
        if (background != null) {
            background.setAlpha(50);
        }

        // 设置日历的整体背景色
        calendarView.setBackgroundColor(requireContext().getResources().getColor(android.R.color.white));
        
        // 设置日期文字颜色
        for (PreviewDay day : previewDays) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(day.getDateMillis());
            
            // 设置日期的文字颜色
            int colorResId = GroupDayDecorator.getGroupColor(day.getGroupIndex());
            int color = requireContext().getResources().getColor(colorResId);
            
            // 通过日期来设置颜色
            calendarView.setDate(day.getDateMillis());
        }
    }
} 