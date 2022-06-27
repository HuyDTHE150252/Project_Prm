package com.fptu.android.project.activity.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fptu.android.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class SignupActivity extends AppCompatActivity {
    private EditText et_email, et_username, et_phone, et_password, et_cfpassword;
    private Button btn_signup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        bindingView();
        bindingAction();
    }

    void bindingView() {
        et_email = findViewById(R.id.et_email);
        et_username = findViewById(R.id.et_username);
        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        et_cfpassword = findViewById(R.id.et_confirm_password);
        btn_signup = findViewById(R.id.btnSignup);
    }

    void bindingAction() {
        btn_signup.setOnClickListener(this::signup1);
    }

    private void signup1(View view) {
        String email, pass, cfpass, phone, username;
        email = et_email.getText().toString();
        pass = et_password.getText().toString();
        cfpass = et_cfpassword.getText().toString();
        phone = et_phone.getText().toString();
        username = et_username.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Vui lòng nhập email!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Vui lòng nhập tên!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Vui lòng nhập sđt!!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Vui lòng nhập password!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(cfpass)) {
            Toast.makeText(this, "Vui lòng xác nhận password!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.equals(pass, cfpass)) {
            Toast.makeText(this, "Vui lòng xác nhận đúng mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Tạo tài khoản không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}