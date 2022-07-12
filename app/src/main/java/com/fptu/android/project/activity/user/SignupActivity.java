package com.fptu.android.project.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.HomePageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    public static final String role = "user";
    private EditText et_email, et_username, et_phone, et_password, et_cfpassword;
    private Button btn_signup;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private TextView loginNow;
    String userID;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(SignupActivity.this, HomePageActivity.class));
//            finish();
//        }
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
        loginNow = findViewById(R.id.loginNow);
    }

    void bindingAction() {
        btn_signup.setOnClickListener(this::onSignup);
        loginNow.setOnClickListener(this::onLoginnow);
    }

    private void onLoginnow(View view) {
        Intent i = new Intent(SignupActivity.this,LoginActivity.class);
        startActivity(i);
    }

    private void onSignup(View view) {
        String email, password, cfpassword, phone, username;
        email = et_email.getText().toString();
        password = et_password.getText().toString();
        cfpassword = et_cfpassword.getText().toString();
        phone = et_phone.getText().toString();
        username = et_username.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your Email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter your Username!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please enter your Phone number!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your Password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(cfpassword)) {
            Toast.makeText(this, "Please enter Confirm password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.equals(password, cfpassword)) {
            Toast.makeText(this, "Please enter correct Confirm password!", Toast.LENGTH_SHORT).show();
            return;
        }
        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    // send verification link

                    FirebaseUser fuser = fAuth.getCurrentUser();
                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(SignupActivity.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                        }
                    });

                    Toast.makeText(SignupActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("users").document(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("uId",userID);
                    user.put("fName",username);
                    user.put("email",email);
                    user.put("phone",phone);
                    user.put("role",role);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: " + e.toString());
                        }
                    });
                    startActivity(new Intent(getApplicationContext(),HomePageActivity.class));

                }else {
                    Toast.makeText(SignupActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    //progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}