package com.shiftschedule.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.shiftschedule.app.R;
import com.shiftschedule.app.model.CalendarDay;
import com.shiftschedule.app.model.ShiftSchedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private List<CalendarDay> days = new ArrayList<>();
    private OnDayClickListener listener;
    private int currentYear;
    private int currentMonth;
    private int selectedPosition = -1;

    public CalendarAdapter(Context context) {
        // 初始化当前日期
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_calendar_day, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        CalendarDay day = days.get(position);
        
        // 设置阳历日期
        holder.textSolarDay.setText(String.valueOf(day.getDayOfMonth()));
        
        // 设置农历日期或节日
        if (day.getLunarDay() != null && !day.getLunarDay().isEmpty()) {
            holder.textLunarDay.setVisibility(View.VISIBLE);
            holder.textLunarDay.setText(day.getLunarDay());
        } else {
            holder.textLunarDay.setVisibility(View.GONE);
        }

        // 设置班次指示器
        holder.shiftIndicators.removeAllViews();
        for (ShiftSchedule shift : day.getShifts()) {
            View indicator = new View(holder.itemView.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    8, // width in dp
                    8  // height in dp
            );
            params.setMargins(2, 0, 2, 0);
            indicator.setLayoutParams(params);
            
            // 根据班次类型设置颜色
            switch (shift.getShiftType()) {
                case "早班":
                    indicator.setBackgroundResource(R.color.shift_morning);
                    break;
                case "中班":
                    indicator.setBackgroundResource(R.color.shift_afternoon);
                    break;
                case "晚班":
                    indicator.setBackgroundResource(R.color.shift_night);
                    break;
            }
            
            holder.shiftIndicators.addView(indicator);
        }

        // 设置选中状态
        holder.itemView.setSelected(position == selectedPosition);
        
        // 设置点击事件
        holder.itemView.setOnClickListener(v -> {
            int oldPosition = selectedPosition;
            selectedPosition = position;
            notifyItemChanged(oldPosition);
            notifyItemChanged(selectedPosition);
            
            if (listener != null) {
                listener.onDayClick(day);
            }
        });
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public void setDays(List<CalendarDay> days) {
        this.days = days;
        notifyDataSetChanged();
    }

    public List<CalendarDay> getDays() {
        return days;
    }

    public void setCurrentMonth(int year, int month) {
        this.currentYear = year;
        this.currentMonth = month;
    }

    public void setOnDayClickListener(OnDayClickListener listener) {
        this.listener = listener;
    }

    public interface OnDayClickListener {
        void onDayClick(CalendarDay day);
    }

    static class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView textSolarDay;
        TextView textLunarDay;
        LinearLayout shiftIndicators;

        CalendarViewHolder(View itemView) {
            super(itemView);
            textSolarDay = itemView.findViewById(R.id.text_solar_day);
            textLunarDay = itemView.findViewById(R.id.text_lunar_day);
            shiftIndicators = itemView.findViewById(R.id.shift_indicators);
        }
    }
} 