package com.fptu.android.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.fptu.android.project.R;
import com.fptu.android.project.model.Order;

public class OrderDetailActivity extends AppCompatActivity {
    TextView tvOrderId,tvorderDate,orderAddress;
    void bidingView(){
        tvOrderId=findViewById(R.id.textViewOrderID);
        tvorderDate=findViewById(R.id.textViewOrderDate);
        orderAddress=findViewById(R.id.textViewAddress);
    }
    void bidingAction(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        bidingView();
        bidingAction();
        Intent intent=getIntent();
        Bundle b= intent.getExtras();
        if(b!=null){
            String orderId=b.getString("orderId");
            String orderDate=b.getString("orderDate");
            String address=b.getString("orderAddress");
            tvOrderId.setText(orderId);
            tvorderDate.setText(orderDate);
            orderAddress.setText(address);
        }else{

        }


    }
}