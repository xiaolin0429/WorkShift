package com.shiftschedule.app.adapter;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.shiftschedule.app.R;
import com.shiftschedule.app.model.CalendarDay;
import com.shiftschedule.app.model.ShiftSchedule;
import com.shiftschedule.app.util.ChineseLunarCalendar;

public class CalendarAdapter extends ListAdapter<CalendarDay, CalendarAdapter.CalendarViewHolder> {
    private OnDayClickListener listener;

    public interface OnDayClickListener {
        void onDayClick(CalendarDay day);
    }

    public CalendarAdapter() {
        super(new DiffUtil.ItemCallback<CalendarDay>() {
            @Override
            public boolean areItemsTheSame(@NonNull CalendarDay oldItem, @NonNull CalendarDay newItem) {
                return oldItem.getDate().equals(newItem.getDate());
            }

            @Override
            public boolean areContentsTheSame(@NonNull CalendarDay oldItem, @NonNull CalendarDay newItem) {
                return oldItem.getDate().equals(newItem.getDate()) &&
                       oldItem.getDayOfWeek().equals(newItem.getDayOfWeek()) &&
                       oldItem.getShifts().equals(newItem.getShifts()) &&
                       oldItem.isCurrentMonth() == newItem.isCurrentMonth() &&
                       oldItem.isToday() == newItem.isToday();
            }
        });
    }

    public void setOnDayClickListener(OnDayClickListener listener) {
        this.listener = listener;
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
        CalendarDay day = getItem(position);
        holder.bind(day, listener);
    }

    static class CalendarViewHolder extends RecyclerView.ViewHolder {
        private final TextView dayText;
        private final TextView weekDayText;
        private final TextView lunarText;
        private final TextView shiftText;
        private final View shiftIndicator;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.text_day);
            weekDayText = itemView.findViewById(R.id.text_weekday);
            lunarText = itemView.findViewById(R.id.text_lunar);
            shiftText = itemView.findViewById(R.id.text_shift);
            shiftIndicator = itemView.findViewById(R.id.view_shift_indicator);
        }

        public void bind(CalendarDay day, OnDayClickListener listener) {
            // 设置日期
            dayText.setText(String.valueOf(day.getDayOfMonth()));
            weekDayText.setText(day.getDayOfWeek());
            
            // 设置农历
            ChineseLunarCalendar lunar = new ChineseLunarCalendar(day.getDate());
            lunarText.setText(lunar.getLunarDayString());
            
            // 设置文字颜色
            int textColor = day.isCurrentMonth() ? 
                    R.color.calendar_text_day : 
                    R.color.calendar_text_day_other_month;
            dayText.setTextColor(ContextCompat.getColor(itemView.getContext(), textColor));
            weekDayText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.calendar_text_weekday));
            lunarText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.calendar_text_lunar));

            // 设置班次
            if (!day.getShifts().isEmpty()) {
                ShiftSchedule shift = day.getShifts().get(0);
                shiftText.setText(shift.getShiftType());
                shiftText.setVisibility(View.VISIBLE);
                
                // 设置班次背景色
                int bgColor;
                switch (shift.getShiftType()) {
                    case "早班":
                        bgColor = R.color.shift_morning;
                        break;
                    case "中班":
                        bgColor = R.color.shift_afternoon;
                        break;
                    case "夜班":
                        bgColor = R.color.shift_night;
                        break;
                    default:
                        bgColor = R.color.shift_rest;
                }
                shiftIndicator.setBackgroundTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(itemView.getContext(), bgColor))
                );
            } else {
                shiftText.setVisibility(View.GONE);
                shiftIndicator.setBackgroundResource(R.drawable.bg_calendar_day);
            }

            // 设置今天的特殊样式
            if (day.isToday()) {
                itemView.setBackgroundResource(R.drawable.bg_calendar_today);
            } else {
                itemView.setBackground(null);
            }

            // 设置点击事件
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDayClick(day);
                }
            });
        }
    }
} 