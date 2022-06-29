package com.fptu.android.project.activity.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.HomePageActivity;
import com.fptu.android.project.activity.fragment.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;


public class LoginActivity extends AppCompatActivity {
    private Button btn_signup, btn_signin, btn_forgot;
    private EditText et_username, et_password;
    private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);
        bindingView();
        bindingAction();
    }
    void bindingView(){
        btn_signup=findViewById(R.id.btn_donothaveaccount);
        btn_signin=findViewById(R.id.btn_signin);
        btn_forgot=findViewById(R.id.btn_forgot_password);
        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);

    }
    void bindingAction(){
        btn_signup.setOnClickListener(this::signup);
        btn_signin.setOnClickListener(this::signin);
        btn_forgot.setOnClickListener(this::forgotpassword);
    }

    private void forgotpassword(View view) {
        final EditText resetMail = new EditText(view.getContext());
        final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
        passwordResetDialog.setTitle("Đặt lại mật khẩu ?");
        passwordResetDialog.setMessage("Nhập địa chỉ email của bạn. " +
                "Link đặt lại mật khẩu sẽ được gửi!");
        passwordResetDialog.setView(resetMail);

        passwordResetDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // extract the email and send reset link
                String mail = resetMail.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(LoginActivity.this, "Link đặt lại mật khẩu đã được gửi. Hãy kiểm tra email.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Lỗi! Không thể gửi link xác nhận." + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        passwordResetDialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // close the dialog
            }
        });

        passwordResetDialog.create().show();

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

        fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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

//    forgotTextLink.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            final EditText resetMail = new EditText(v.getContext());
//            final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
//            passwordResetDialog.setTitle("Reset Password ?");
//            passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
//            passwordResetDialog.setView(resetMail);
//
//            passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    // extract the email and send reset link
//                    String mail = resetMail.getText().toString();
//                    fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(Login.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(Login.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//            });
//
//            passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    // close the dialog
//                }
//            });
//
//            passwordResetDialog.create().show();
//
//        }
//    });
    private void signup(View view) {
        Intent i =new Intent(LoginActivity.this,SignupActivity.class);
        startActivity(i);
    }

}