package com.fptu.android.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fptu.android.project.R;
import com.fptu.android.project.model.Order;

public class OrderDetailActivity extends AppCompatActivity {
    TextView tvOrderId,orderDate,orderAddress;
    void bidingView(){
        tvOrderId=findViewById(R.id.textViewOrderID);
        orderDate=findViewById(R.id.textViewOrderDate);
        orderAddress=findViewById(R.id.textViewAddress);
    }
    void bidingAction(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        bidingView();
        bidingAction();
        Order order=(Order)getIntent().getSerializableExtra("detailedOrder");
        tvOrderId.setText(order.getDocumentId());
        orderDate.setText(order.getCurrentTime()+" "+order.getCurrentDate());
        orderAddress.setText(order.getAddress());
    }
}