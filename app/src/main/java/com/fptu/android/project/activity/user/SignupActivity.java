package com.fptu.android.project.activity.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.fptu.android.project.R;

public class SignupActivity extends AppCompatActivity {
    private EditText et_email,et_username,et_phone,_et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
}