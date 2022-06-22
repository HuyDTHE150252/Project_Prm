package com.fptu.android.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.admin.AdminActivity;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
////        findViewById(R.id.BarChart).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getAcivity();
//            }
//        });
    }

    private void getAcivity() {
        startActivity(new Intent(this, AdminActivity.class));
    }
}