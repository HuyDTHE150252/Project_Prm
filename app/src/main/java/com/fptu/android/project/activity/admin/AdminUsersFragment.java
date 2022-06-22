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
import com.fptu.android.project.activity.admin.adapter.UsersItemAdapter;
import com.fptu.android.project.model.User;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class AdminUsersFragment extends Fragment {
    private PieChart pieChart;
    private PieData pieData;
    private PieDataSet pieDataSet;

    private RecyclerView usersRCV;
    private UsersItemAdapter adapter;
    private ArrayList<User> userData;

    private void bindingView(View v) {
        usersRCV = v.findViewById(R.id.users_rcv);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_users, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if (view != null) {
            bindingView(view);
            loadingData();
            renderPieChart(R.id.users_chart_status, view);
            renderPieChart(R.id.users_chart_activities, view);
        }

    }


    private void renderPieChart(int id, View view) {
        switch (id) {
            case R.id.users_chart_status:
                pieChart = view.findViewById(R.id.users_chart_status);
                break;
            case R.id.users_chart_activities:
                pieChart = view.findViewById(R.id.users_chart_activities);
                break;
        }


        pieChart.setData(pieData);
        pieChart.setRotationEnabled(true);
        pieChart.setDescription(new Description());
        pieChart.setHoleRadius(35f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("PieChart");
        pieChart.setCenterTextSize(10);
        pieChart.setDrawEntryLabels(true);

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        float[] yData = {25, 40, 70};
        String[] xData = {"January", "February", "January"};

        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], i));
        }
        for (int i = 0; i < xData.length; i++) {
            xEntrys.add(xData[i]);
        }

        pieDataSet = new PieDataSet(yEntrys, "Employee Sales");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);


        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void loadingData() {
        ArrayList<User> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(new User());
        }
        adapter = new UsersItemAdapter();
        adapter.setUserList(data);
        usersRCV.setAdapter(adapter);
        usersRCV.setLayoutManager(new LinearLayoutManager(getView().getContext()));
    }

}
