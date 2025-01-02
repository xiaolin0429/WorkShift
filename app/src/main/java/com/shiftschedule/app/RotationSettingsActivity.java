package com.shiftschedule.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shiftschedule.app.adapter.RotationPatternAdapter;
import com.shiftschedule.app.model.RotationPattern;
import com.shiftschedule.app.viewmodel.RotationViewModel;

public class RotationSettingsActivity extends AppCompatActivity {
    private RotationViewModel viewModel;
    private RotationPatternAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation_settings);

        // 设置Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("轮班设置");
        }

        // 初始化ViewModel
        viewModel = new ViewModelProvider(this).get(RotationViewModel.class);

        // 设置RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_patterns);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 初始化适配器
        adapter = new RotationPatternAdapter(new RotationPatternAdapter.OnPatternClickListener() {
            @Override
            public void onEditClick(RotationPattern pattern) {
                Intent intent = new Intent(RotationSettingsActivity.this, EditRotationPatternActivity.class);
                intent.putExtra("pattern_id", pattern.getId());
                startActivity(intent);
            }

            @Override
            public void onPreviewClick(RotationPattern pattern) {
                Intent intent = new Intent(RotationSettingsActivity.this, PreviewPatternActivity.class);
                intent.putExtra("pattern_id", pattern.getId());
                startActivity(intent);
            }

            @Override
            public void onActivateClick(RotationPattern pattern) {
                new AlertDialog.Builder(RotationSettingsActivity.this)
                        .setTitle("激活轮班模式")
                        .setMessage("确定要激活这个轮班模式吗？这将停用当前激活的模式。")
                        .setPositiveButton("确定", (dialog, which) -> {
                            viewModel.activatePattern(pattern);
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
        recyclerView.setAdapter(adapter);

        // 设置添加按钮
        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditRotationPatternActivity.class);
            startActivity(intent);
        });

        // 观察数据变化
        viewModel.getAllPatterns().observe(this, patterns -> {
            adapter.submitList(patterns);
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