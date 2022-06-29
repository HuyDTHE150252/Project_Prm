package com.fptu.android.project.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.HomePageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;


public class LoginActivity extends AppCompatActivity {
    private Button btn_signup, btn_signin;
    private EditText et_username, et_password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        bindingView();
        bindingAction();
    }
    void bindingView(){
        btn_signup=findViewById(R.id.btn_donothaveaccount);
        btn_signin=findViewById(R.id.btn_signin);
        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
    }
    void bindingAction(){
        btn_signup.setOnClickListener(this::signup);
        btn_signin.setOnClickListener(this::signin);
    }

    private void signin(View view) {

        String email,pass;
        email=et_username.getText().toString();
        pass=et_password.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Vui lòng nhập email!!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Vui lòng nhập password!!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(pass.length()<=5){
            Toast.makeText(this,"Password >= 6 char!!",Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(LoginActivity.this, HomePageActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Đăng nhập không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        }


    private void signup(View view) {
        Intent i =new Intent(LoginActivity.this,SignupActivity.class);
        startActivity(i);
    }

}