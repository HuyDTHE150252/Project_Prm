package com.fptu.android.project.activity.admin;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.admin.adapter.DashboardItemAdapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class AdminDashboardFragment extends Fragment {
    private LineChart lineChart;
    private LineDataSet lineDataSet;
    private LineData lineData;
    private ArrayList lineEntries;
    private RecyclerView dashboardRCV;
    private DashboardItemAdapter adapter;

    private ArrayList<Integer> itemData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_admin_dashboard, container, false);
    }

    private void bindingView(View v) {
        dashboardRCV = v.findViewById(R.id.dashboard_rcv);
    }

    private void loadingData(){

        itemData = new ArrayList<>();
        itemData.add(1);
        itemData.add(2);
        itemData.add(3);
        adapter = new DashboardItemAdapter();
        adapter.setItems(itemData);
        dashboardRCV.setAdapter(adapter);
        dashboardRCV.setLayoutManager(new LinearLayoutManager(getView().getContext()));
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {
            bindingView(view);
            loadingData();
            lineChart = view.findViewById(R.id.dashboard_lineChart);
            lineEntries = new ArrayList<>();
            lineEntries.add(new Entry(2, 0));
            lineEntries.add(new Entry(3, 3));
            lineEntries.add(new Entry(4, 1));
            lineEntries.add(new Entry(6, 1));
            lineEntries.add(new Entry(8, 3));
            lineEntries.add(new Entry(7, 4));

            lineDataSet = new LineDataSet(lineEntries, "line chart1");
            lineData = new LineData(lineDataSet);
            lineChart.setData(lineData);
            lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
            lineDataSet.setValueTextColor(Color.BLACK);
            lineDataSet.setValueTextSize(18f);

        }
    }
}