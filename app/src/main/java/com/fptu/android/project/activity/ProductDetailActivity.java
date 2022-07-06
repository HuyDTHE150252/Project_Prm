package com.fptu.android.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.fptu.android.project.R;
import com.fptu.android.project.adapter.FeedbackAdapter;
import com.fptu.android.project.model.Feedback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    private RecyclerView rcvFeedback;
    private FeedbackAdapter feedbackadapter;

    private void bindingView(){
        rcvFeedback = findViewById(R.id.rcvFeedback);
        feedbackadapter = new FeedbackAdapter(this);
    }

    private List<Feedback> getListFeedback() {
        List<Feedback> list = new ArrayList<>();
        list.add(new Feedback("abc123","Ngon",5,"29/06/2022"));
        list.add(new Feedback("abc1234","Ngon",5,"29/06/2022"));
        list.add(new Feedback("abc12345","Ngon",5,"29/06/2022"));
        list.add(new Feedback("abc123456","Ngon",5,"29/06/2022"));
        list.add(new Feedback("abc1234567","Ngon",5,"29/06/2022"));

        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        bindingView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvFeedback.setLayoutManager(linearLayoutManager);
        feedbackadapter.setData(getListFeedback());
        rcvFeedback.setAdapter(feedbackadapter);
    }
}