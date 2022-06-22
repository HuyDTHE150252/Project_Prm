package com.fptu.android.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fptu.android.project.DatabaseTest;
import com.fptu.android.project.R;

public class ActivityFake extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake);
        findViewById(R.id.btnTest).setOnClickListener(this::OnClickTest);
        findViewById(R.id.btnGet).setOnClickListener(this::OnClickGet);
    }

    private void OnClickGet(View view) {
        DatabaseTest dbt = new DatabaseTest();
        dbt.getData();
    }

    private void OnClickTest(View view) {
        DatabaseTest dbt = new DatabaseTest();
        dbt.Test();
    }
}