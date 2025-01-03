package com.shiftschedule.app.adapter;

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

public class ShiftAdapter extends ListAdapter<ShiftSchedule, ShiftAdapter.ShiftViewHolder> {
    private final OnShiftClickListener listener;

    public interface OnShiftClickListener {
        void onShiftClick(ShiftSchedule shift);
    }

    public ShiftAdapter(OnShiftClickListener listener) {
        super(new DiffUtil.ItemCallback<ShiftSchedule>() {
            @Override
            public boolean areItemsTheSame(@NonNull ShiftSchedule oldItem, @NonNull ShiftSchedule newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull ShiftSchedule oldItem, @NonNull ShiftSchedule newItem) {
                return oldItem.equals(newItem);
            }
        });
        this.listener = listener;
    }

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
        holder.bind(shift, listener);
    }

    static class ShiftViewHolder extends RecyclerView.ViewHolder {
        private final TextView shiftTypeText;
        private final TextView shiftTimeText;
        private final TextView shiftNoteText;
        private final View colorIndicator;

        public ShiftViewHolder(@NonNull View itemView) {
            super(itemView);
            shiftTypeText = itemView.findViewById(R.id.text_shift_type);
            shiftTimeText = itemView.findViewById(R.id.text_shift_time);
            shiftNoteText = itemView.findViewById(R.id.text_shift_note);
            colorIndicator = itemView.findViewById(R.id.shift_color_indicator);
        }

        public void bind(ShiftSchedule shift, OnShiftClickListener listener) {
            shiftTypeText.setText(shift.getShiftType());
            shiftTimeText.setText(shift.getTimeRange());
            
            if (shift.getNote() != null && !shift.getNote().isEmpty()) {
                shiftNoteText.setText(shift.getNote());
                shiftNoteText.setVisibility(View.VISIBLE);
            } else {
                shiftNoteText.setVisibility(View.GONE);
            }

            // 设置颜色指示器
            int colorRes;
            switch (shift.getShiftType()) {
                case "早班":
                    colorRes = R.color.shift_morning;
                    break;
                case "中班":
                    colorRes = R.color.shift_afternoon;
                    break;
                case "夜班":
                    colorRes = R.color.shift_night;
                    break;
                default:
                    colorRes = R.color.shift_rest;
            }
            colorIndicator.setBackgroundResource(colorRes);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onShiftClick(shift);
                }
            });
        }
    }
} 