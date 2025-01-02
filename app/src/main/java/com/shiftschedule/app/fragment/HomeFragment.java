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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shiftschedule.app.EditShiftActivity;
import com.shiftschedule.app.R;
import com.shiftschedule.app.adapter.ShiftAdapter;
import com.shiftschedule.app.util.LogUtil;
import com.shiftschedule.app.viewmodel.ShiftViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private ShiftViewModel shiftViewModel;
    private ShiftAdapter adapter;
    private View emptyView;
    private RecyclerView recyclerView;
    private TextView todayDateText;
    private FloatingActionButton fabAddShift;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 初始化视图
        recyclerView = view.findViewById(R.id.recycler_shifts);
        emptyView = view.findViewById(R.id.empty_view);
        todayDateText = view.findViewById(R.id.text_today_date);
        fabAddShift = view.findViewById(R.id.fab_add_shift);

        // 设置日期
        String today = new SimpleDateFormat("yyyy年M月d日 EEEE", Locale.CHINESE)
                .format(new Date());
        todayDateText.setText(today);

        // 设置RecyclerView
        adapter = new ShiftAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        // 初始化ViewModel
        shiftViewModel = new ViewModelProvider(requireActivity()).get(ShiftViewModel.class);
        
        // 观察数据变化
        shiftViewModel.getSelectedDateShifts().observe(getViewLifecycleOwner(), shifts -> {
            adapter.submitList(shifts);
            updateEmptyView(shifts == null || shifts.isEmpty());
        });

        // 设置FAB点击事件
        fabAddShift.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), EditShiftActivity.class);
            startActivity(intent);
        });
    }

    private void updateEmptyView(boolean isEmpty) {
        emptyView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }
} 