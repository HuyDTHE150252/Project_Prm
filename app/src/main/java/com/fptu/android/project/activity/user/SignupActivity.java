package com.fptu.android.project.activity.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.fptu.android.project.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private EditText et_email,et_username,et_phone,_et_password;
    private Button btn_signup;
    private FirebaseAuth mAuth;
    void bindingView(){
        et_email = findViewById(R.id.et_email);
    }
    void bindingAction(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
}