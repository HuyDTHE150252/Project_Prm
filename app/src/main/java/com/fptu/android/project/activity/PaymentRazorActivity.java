package com.fptu.android.project.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fptu.android.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class PaymentRazorActivity extends AppCompatActivity implements PaymentResultListener {
    TextView totalPrice;
    Button payBtn;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private void bidingView() {

        payBtn=findViewById(R.id.pay_btn);

    }

    void bidingAction(){
         payBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String sAmount="100";
                 int amount=Math.round(Float.parseFloat(sAmount)*100);
                 //razor check
                 Checkout checkout=new Checkout();
                 //set key id
                 checkout.setKeyID("rzp_test_manmXiBRI19pqj");
                 //set image
                 checkout.setImage(R.drawable.rzp);
                 //init  json
                 JSONObject object= new JSONObject();
                 try {
                     object.put("name","ducdd");
                     object.put("description","Payment");
                     object.put("theme.color","#0093DD");
                     object.put("currency","INR");
                     object.put("amount",amount);
                     object.put("prefill.contact","9876543210");
                     object.put("prefill.email","nguyenvanduc14012000@gmail.com");

                     //Open razor
                     checkout.open(PaymentRazorActivity.this,object);
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

             }
         });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        bidingView();
        bidingAction();



    }


    @Override
    public void onPaymentSuccess(String s) {
        String sAmount="100";
//        int amount=Math.round(Float.parseFloat(sAmount)*100);
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Payment Id");
        builder.setMessage(s);
        builder.show();
        HashMap<String,String> map=new HashMap<>();
//        map.put("paymentID", String.valueOf(builder.show()));
        map.put("status","Payment Successfully");
        map.put("totalAmount",sAmount);
        firestore.collection("Payment").document(auth.getUid()).
                collection("CurrentUser").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PaymentRazorActivity.this, "Payment Success", Toast.LENGTH_SHORT).show();


                        }
                    }
                });
        Intent intent = new Intent(PaymentRazorActivity.this, HomePageActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s) {
           Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}