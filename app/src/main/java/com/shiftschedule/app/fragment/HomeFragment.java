package com.shiftschedule.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.shiftschedule.app.EditShiftActivity;
import com.shiftschedule.app.EditRotationPatternActivity;
import com.shiftschedule.app.R;
import com.shiftschedule.app.adapter.ShiftAdapter;
import com.shiftschedule.app.model.ShiftSchedule;
import com.shiftschedule.app.viewmodel.ShiftViewModel;

public class HomeFragment extends Fragment implements ShiftAdapter.OnShiftClickListener {
    private ShiftViewModel viewModel;
    private ShiftAdapter adapter;
    private TextView todayShiftText;
    private TextView shiftTimeText;
    private TextView errorText;
    private RecyclerView weekShiftsRecycler;
    private SwipeRefreshLayout swipeRefresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 初始化视图
        todayShiftText = view.findViewById(R.id.text_today_shift);
        shiftTimeText = view.findViewById(R.id.text_shift_time);
        errorText = view.findViewById(R.id.text_error);
        weekShiftsRecycler = view.findViewById(R.id.recycler_week_shifts);
        swipeRefresh = view.findViewById(R.id.swipe_refresh);

        // 设置RecyclerView
        weekShiftsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ShiftAdapter(this);
        weekShiftsRecycler.setAdapter(adapter);

        // 设置下拉刷新
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(() -> {
            viewModel.refreshData();
        });

        // 设置按钮点击事件
        view.findViewById(R.id.button_add_shift).setOnClickListener(v -> 
            startActivity(new Intent(requireContext(), EditShiftActivity.class)));

        view.findViewById(R.id.button_edit_pattern).setOnClickListener(v -> 
            startActivity(new Intent(requireContext(), EditRotationPatternActivity.class)));

        FloatingActionButton fab = view.findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> 
            startActivity(new Intent(requireContext(), EditShiftActivity.class)));

        // 初始化ViewModel
        viewModel = new ViewModelProvider(this).get(ShiftViewModel.class);

        // 观察数据变化
        viewModel.getTodayShift().observe(getViewLifecycleOwner(), shift -> {
            if (shift != null) {
                todayShiftText.setText(shift.getShiftType());
                shiftTimeText.setText(shift.getTimeRange());
            } else {
                todayShiftText.setText("今天休息");
                shiftTimeText.setText("");
            }
        });

        viewModel.getWeekShifts().observe(getViewLifecycleOwner(), shifts -> {
            adapter.submitList(shifts);
        });

        // 观察加载状态
        viewModel.isLoading().observe(getViewLifecycleOwner(), isLoading -> {
            swipeRefresh.setRefreshing(isLoading);
        });

        // 观察错误信息
        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                errorText.setText(error);
                errorText.setVisibility(View.VISIBLE);
                Snackbar.make(view, error, Snackbar.LENGTH_LONG)
                        .setAction("重试", v -> viewModel.refreshData())
                        .show();
            } else {
                errorText.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onShiftClick(ShiftSchedule shift) {
        Intent intent = new Intent(requireContext(), EditShiftActivity.class);
        intent.putExtra("shift_id", shift.getId());
        startActivity(intent);
    }
} 