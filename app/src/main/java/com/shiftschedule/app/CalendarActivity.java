package com.shiftschedule.app;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.shiftschedule.app.adapter.ShiftAdapter;
import com.shiftschedule.app.util.LogUtil;
import com.shiftschedule.app.viewmodel.ShiftViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";
    private ShiftViewModel shiftViewModel;
    private ShiftAdapter adapter;
    private TextView textEmpty;
    private RecyclerView recyclerView;
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // 设置Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {  // 添加null检查
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 初始化ViewModel
        shiftViewModel = new ViewModelProvider(this).get(ShiftViewModel.class);

        // 初始化视图和设置
        initViews();
        setupRecyclerView();
        setupCalendar();
        observeShifts();
    }

    private void initViews() {
        calendarView = findViewById(R.id.calendar_view);
        recyclerView = findViewById(R.id.recycler_shifts);
        textEmpty = findViewById(R.id.text_empty);
    }

    private void setupCalendar() {
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String date = String.format(Locale.getDefault(), "%d-%02d-%02d", 
                year, month + 1, dayOfMonth);
            LogUtil.d(TAG, "Selected date: " + date);
            shiftViewModel.setSelectedDate(date);
        });

        // 设置当前日期
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(new Date());
        shiftViewModel.setSelectedDate(today);
    }

    private void setupRecyclerView() {
        adapter = new ShiftAdapter(null);  // 不需要点击监听
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void observeShifts() {
        shiftViewModel.getSelectedDateShifts().observe(this, shifts -> {
            LogUtil.d(TAG, "Shifts updated, size: " + (shifts != null ? shifts.size() : 0));
            adapter.submitList(shifts);
            
            // 更新空视图
            boolean isEmpty = shifts == null || shifts.isEmpty();
            textEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        });
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