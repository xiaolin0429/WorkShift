package com.shiftschedule.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.shiftschedule.app.PreviewPatternActivity.PreviewDay;
import com.shiftschedule.app.R;

public class PreviewDayAdapter extends ListAdapter<PreviewDay, PreviewDayAdapter.PreviewDayViewHolder> {

    public PreviewDayAdapter() {
        super(new DiffUtil.ItemCallback<PreviewDay>() {
            @Override
            public boolean areItemsTheSame(@NonNull PreviewDay oldItem, @NonNull PreviewDay newItem) {
                return oldItem.getDayInfo().equals(newItem.getDayInfo());
            }

            @Override
            public boolean areContentsTheSame(@NonNull PreviewDay oldItem, @NonNull PreviewDay newItem) {
                return oldItem.getDayInfo().equals(newItem.getDayInfo()) &&
                       oldItem.getGroupInfo().equals(newItem.getGroupInfo()) &&
                       oldItem.isNewCycle() == newItem.isNewCycle();
            }
        });
    }

    @NonNull
    @Override
    public PreviewDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_preview_day, parent, false);
        return new PreviewDayViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewDayViewHolder holder, int position) {
        PreviewDay day = getItem(position);
        holder.bind(day);
    }

    static class PreviewDayViewHolder extends RecyclerView.ViewHolder {
        private final TextView dayInfoText;
        private final TextView groupInfoText;
        private final TextView cycleMarkerText;

        PreviewDayViewHolder(View itemView) {
            super(itemView);
            dayInfoText = itemView.findViewById(R.id.text_day_info);
            groupInfoText = itemView.findViewById(R.id.text_group_info);
            cycleMarkerText = itemView.findViewById(R.id.text_cycle_marker);
        }

        void bind(PreviewDay day) {
            dayInfoText.setText(day.getDayInfo());
            groupInfoText.setText(day.getGroupInfo());
            cycleMarkerText.setVisibility(day.isNewCycle() ? View.VISIBLE : View.GONE);
        }
    }
} 