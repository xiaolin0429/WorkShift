package com.shiftschedule.app;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.shiftschedule.app.adapter.CalendarAdapter;
import com.shiftschedule.app.model.CalendarDay;
import com.shiftschedule.app.util.CalendarUtils;
import com.shiftschedule.app.viewmodel.ShiftViewModel;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity implements CalendarAdapter.OnDayClickListener {
    private ShiftViewModel shiftViewModel;
    private CalendarAdapter calendarAdapter;
    private RecyclerView recyclerView;
    private TextView monthText;
    private Calendar currentCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        shiftViewModel = new ViewModelProvider(this).get(ShiftViewModel.class);
        currentCalendar = Calendar.getInstance();

        initViews();
        setupCalendarView();
        updateMonthText();
        loadShifts();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_calendar);
        monthText = findViewById(R.id.text_month);

        findViewById(R.id.button_prev_month).setOnClickListener(v -> navigateMonth(-1));
        findViewById(R.id.button_next_month).setOnClickListener(v -> navigateMonth(1));
    }

    private void setupCalendarView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 7));
        calendarAdapter = new CalendarAdapter();
        calendarAdapter.setOnDayClickListener(this);
        recyclerView.setAdapter(calendarAdapter);
    }

    private void updateMonthText() {
        int year = currentCalendar.get(Calendar.YEAR);
        int month = currentCalendar.get(Calendar.MONTH) + 1;
        monthText.setText(String.format("%d年%d月", year, month));
    }

    private void loadShifts() {
        int year = currentCalendar.get(Calendar.YEAR);
        int month = currentCalendar.get(Calendar.MONTH);
        String startDate = CalendarUtils.formatDate(year, month, 1);
        String endDate = CalendarUtils.formatDate(year, month + 1, 0);

        shiftViewModel.getShiftsByDateRange(startDate, endDate).observe(this, shifts -> {
            calendarAdapter.submitList(CalendarUtils.getMonthCalendar(year, month, shifts));
        });
    }

    private void navigateMonth(int offset) {
        currentCalendar.add(Calendar.MONTH, offset);
        updateMonthText();
        loadShifts();
    }

    @Override
    public void onDayClick(CalendarDay day) {
        // TODO: 处理日期点击事件
    }
} 