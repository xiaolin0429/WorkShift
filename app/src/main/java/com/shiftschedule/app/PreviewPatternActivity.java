package com.shiftschedule.app;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.shiftschedule.app.adapter.PreviewPagerAdapter;
import com.shiftschedule.app.model.RotationPattern;
import com.shiftschedule.app.viewmodel.RotationViewModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PreviewPatternActivity extends AppCompatActivity {
    private RotationViewModel viewModel;
    private PreviewPagerAdapter adapter;
    private TextView summaryText;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private int patternId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_pattern);

        // 设置Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 初始化ViewModel
        viewModel = new ViewModelProvider(this).get(RotationViewModel.class);

        // 获取传入的模式ID
        patternId = getIntent().getIntExtra("pattern_id", -1);
        if (patternId == -1) {
            finish();
            return;
        }

        // 初始化视图
        initViews();
        loadPattern();
    }

    private void initViews() {
        summaryText = findViewById(R.id.text_summary);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
    }

    private void loadPattern() {
        viewModel.getPatternById(patternId).observe(this, pattern -> {
            if (pattern != null) {
                updatePreview(pattern);
            }
        });
    }

    private void updatePreview(RotationPattern pattern) {
        // 设置标题
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(pattern.getName() + " 预览");
        }

        // 更新摘要信息
        String summary = String.format("共%d个班组，%d天一个周期，持续%d天\n从今天开始预览",
                pattern.getGroupCount(), pattern.getCycleDays(), pattern.getDurationDays());
        summaryText.setText(summary);

        // 生成预览数据
        List<PreviewDay> previewDays = generatePreviewDays(pattern);

        // 设置ViewPager
        if (adapter == null) {
            adapter = new PreviewPagerAdapter(this, previewDays);
            viewPager.setAdapter(adapter);

            // 设置TabLayout
            new TabLayoutMediator(tabLayout, viewPager,
                    (tab, position) -> tab.setText(position == 0 ? "列表视图" : "日历视图")
            ).attach();
        } else {
            adapter.updatePreviewDays(previewDays);
        }
    }

    private List<PreviewDay> generatePreviewDays(RotationPattern pattern) {
        List<PreviewDay> days = new ArrayList<>();
        int groupCount = pattern.getGroupCount();
        int cycleDays = pattern.getCycleDays();
        int durationDays = pattern.getDurationDays();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 E", Locale.CHINESE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        for (int day = 1; day <= durationDays; day++) {
            int cycleDay = ((day - 1) % cycleDays) + 1;
            int groupIndex = ((day - 1) % cycleDays) % groupCount;
            
            // 生成日期字符串
            String dateStr = dateFormat.format(calendar.getTime());
            long dateMillis = calendar.getTimeInMillis();
            calendar.add(Calendar.DAY_OF_MONTH, 1);

            // 生成班组信息
            String dayInfo = String.format("第%d天 · %s", day, dateStr);
            String groupInfo = String.format("班组%d", groupIndex + 1);
            
            // 判断是否是新的周期开始
            boolean isNewCycle = cycleDay == 1;
            
            days.add(new PreviewDay(dayInfo, groupInfo, isNewCycle, groupIndex, dateMillis));
        }

        return days;
    }

    public static class PreviewDay {
        private final String dayInfo;
        private final String groupInfo;
        private final boolean isNewCycle;
        private final int groupIndex;
        private final long dateMillis;

        PreviewDay(String dayInfo, String groupInfo, boolean isNewCycle, int groupIndex, long dateMillis) {
            this.dayInfo = dayInfo;
            this.groupInfo = groupInfo;
            this.isNewCycle = isNewCycle;
            this.groupIndex = groupIndex;
            this.dateMillis = dateMillis;
        }

        public String getDayInfo() {
            return dayInfo;
        }

        public String getGroupInfo() {
            return groupInfo;
        }

        public boolean isNewCycle() {
            return isNewCycle;
        }

        public int getGroupIndex() {
            return groupIndex;
        }

        public long getDateMillis() {
            return dateMillis;
        }
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