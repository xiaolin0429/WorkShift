package com.shiftschedule.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.shiftschedule.app.R;
import com.shiftschedule.app.model.ShiftSchedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ShiftAdapter extends ListAdapter<ShiftSchedule, ShiftAdapter.ShiftViewHolder> {
    private OnShiftClickListener listener;

    public ShiftAdapter(OnShiftClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<ShiftSchedule> DIFF_CALLBACK = 
            new DiffUtil.ItemCallback<ShiftSchedule>() {
        @Override
        public boolean areItemsTheSame(@NonNull ShiftSchedule oldItem, 
                                     @NonNull ShiftSchedule newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ShiftSchedule oldItem, 
                                        @NonNull ShiftSchedule newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public ShiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shift, parent, false);
        return new ShiftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShiftViewHolder holder, int position) {
        ShiftSchedule shift = getItem(position);
        
        // 设置班次类型
        holder.textShiftType.setText(shift.getShiftType());
        
        // 设置班次时间
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String timeRange = String.format("%s - %s",
                timeFormat.format(new Date(shift.getStartTime())),
                timeFormat.format(new Date(shift.getEndTime())));
        holder.textShiftTime.setText(timeRange);
        
        // 设置备注
        if (shift.getNote() != null && !shift.getNote().isEmpty()) {
            holder.textShiftNote.setVisibility(View.VISIBLE);
            holder.textShiftNote.setText(shift.getNote());
        } else {
            holder.textShiftNote.setVisibility(View.GONE);
        }
        
        // 设置颜色指示器
        holder.shiftColorIndicator.setBackgroundColor(getShiftColor(shift.getShiftType(), holder.itemView.getContext()));
        
        // 设置点击事件
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onShiftClick(shift);
            }
        });
    }

    private int getShiftColor(String shiftType, Context context) {
        switch (shiftType) {
            case "早班":
                return context.getColor(R.color.shift_morning);
            case "中班":
                return context.getColor(R.color.shift_afternoon);
            case "晚班":
                return context.getColor(R.color.shift_night);
            default:
                return context.getColor(R.color.purple_500);
        }
    }

    public interface OnShiftClickListener {
        void onShiftClick(ShiftSchedule shift);
    }

    static class ShiftViewHolder extends RecyclerView.ViewHolder {
        TextView textShiftType;
        TextView textShiftTime;
        TextView textShiftNote;
        View shiftColorIndicator;

        ShiftViewHolder(View itemView) {
            super(itemView);
            textShiftType = itemView.findViewById(R.id.text_shift_type);
            textShiftTime = itemView.findViewById(R.id.text_shift_time);
            textShiftNote = itemView.findViewById(R.id.text_shift_note);
            shiftColorIndicator = itemView.findViewById(R.id.shift_color_indicator);
        }
    }
} 