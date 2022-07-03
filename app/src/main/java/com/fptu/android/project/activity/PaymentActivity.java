package com.fptu.android.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.fptu.android.project.R;

public class PaymentActivity extends AppCompatActivity {
    TextView subTotal,discount,shipping,total;
    void bidingView(){
        subTotal=findViewById(R.id.sub_total);
        discount=findViewById(R.id.discount);
        shipping=findViewById(R.id.tvShipping);
        total=findViewById(R.id.total_amt);
    }
    void bidingAction(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    }
}