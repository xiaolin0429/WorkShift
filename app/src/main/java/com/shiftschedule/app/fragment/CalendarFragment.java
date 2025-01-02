package com.shiftschedule.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.shiftschedule.app.R;
import com.shiftschedule.app.adapter.ShiftAdapter;
import com.shiftschedule.app.util.LogUtil;
import com.shiftschedule.app.viewmodel.ShiftViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarFragment extends Fragment {
    private static final String TAG = "CalendarFragment";
    private ShiftViewModel shiftViewModel;
    private ShiftAdapter adapter;
    private TextView textEmpty;
    private RecyclerView recyclerView;
    private CalendarView calendarView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 初始化ViewModel
        shiftViewModel = new ViewModelProvider(requireActivity()).get(ShiftViewModel.class);

        // 初始化视图
        initViews(view);
        setupRecyclerView();
        setupCalendar();
        observeShifts();
    }

    private void initViews(View view) {
        calendarView = view.findViewById(R.id.calendar_view);
        recyclerView = view.findViewById(R.id.recycler_shifts);
        textEmpty = view.findViewById(R.id.text_empty);
    }

    private void setupCalendar() {
        if (calendarView == null || shiftViewModel == null) {
            LogUtil.e(TAG, "setupCalendar: calendarView or shiftViewModel is null");
            return;
        }

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
        adapter = new ShiftAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
    }

    private void observeShifts() {
        shiftViewModel.getSelectedDateShifts().observe(getViewLifecycleOwner(), shifts -> {
            LogUtil.d(TAG, "Shifts updated, size: " + (shifts != null ? shifts.size() : 0));
            adapter.submitList(shifts);
            
            // 更新空视图
            boolean isEmpty = shifts == null || shifts.isEmpty();
            textEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        });
    }
} 