package com.shiftschedule.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.shiftschedule.app.R;
import com.shiftschedule.app.adapter.PreviewDayAdapter;
import com.shiftschedule.app.PreviewPatternActivity.PreviewDay;
import java.util.List;

public class ListPreviewFragment extends Fragment {
    private PreviewDayAdapter adapter;
    private List<PreviewDay> previewDays;

    public static ListPreviewFragment newInstance(List<PreviewDay> previewDays) {
        ListPreviewFragment fragment = new ListPreviewFragment();
        fragment.previewDays = previewDays;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_preview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_preview);
        adapter = new PreviewDayAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        if (previewDays != null) {
            adapter.submitList(previewDays);
        }
    }

    public void updatePreviewDays(List<PreviewDay> previewDays) {
        this.previewDays = previewDays;
        if (adapter != null) {
            adapter.submitList(previewDays);
        }
    }
} 