package com.shiftschedule.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.shiftschedule.app.R;
import com.shiftschedule.app.model.RotationPattern;

public class RotationPatternAdapter extends ListAdapter<RotationPattern, RotationPatternAdapter.PatternViewHolder> {
    private final OnPatternClickListener listener;

    public interface OnPatternClickListener {
        void onEditClick(RotationPattern pattern);
        void onPreviewClick(RotationPattern pattern);
        void onActivateClick(RotationPattern pattern);
    }

    public RotationPatternAdapter(OnPatternClickListener listener) {
        super(new DiffUtil.ItemCallback<RotationPattern>() {
            @Override
            public boolean areItemsTheSame(@NonNull RotationPattern oldItem, @NonNull RotationPattern newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull RotationPattern oldItem, @NonNull RotationPattern newItem) {
                return oldItem.equals(newItem);
            }
        });
        this.listener = listener;
    }

    @NonNull
    @Override
    public PatternViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rotation_pattern, parent, false);
        return new PatternViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatternViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class PatternViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private final TextView infoText;
        private final Button editButton;
        private final Button previewButton;
        private final Button activateButton;

        PatternViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.text_pattern_name);
            infoText = itemView.findViewById(R.id.text_pattern_info);
            editButton = itemView.findViewById(R.id.button_edit);
            previewButton = itemView.findViewById(R.id.button_preview);
            activateButton = itemView.findViewById(R.id.button_activate);
        }

        void bind(RotationPattern pattern) {
            nameText.setText(pattern.getName());
            String info = String.format("%d个班组，%d天一个周期",
                    pattern.getGroupCount(), pattern.getCycleDays());
            infoText.setText(info);

            editButton.setOnClickListener(v -> listener.onEditClick(pattern));
            previewButton.setOnClickListener(v -> listener.onPreviewClick(pattern));
            activateButton.setOnClickListener(v -> listener.onActivateClick(pattern));
            
            // 设置激活按钮的状态
            activateButton.setEnabled(!pattern.isActive());
            activateButton.setText(pattern.isActive() ? "已激活" : "激活");
        }
    }
} 