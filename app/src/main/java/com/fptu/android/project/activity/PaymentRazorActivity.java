package com.fptu.android.project.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fptu.android.project.R;
import com.fptu.android.project.model.Order;
import com.fptu.android.project.model.Product;
import com.fptu.android.project.service.NotificationService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class PaymentRazorActivity extends AppCompatActivity implements PaymentResultListener {
    TextView totalPrice;
    Button payBtn;
    FirebaseFirestore firestore;
    FirebaseAuth auth;


    private void bidingView() {
        payBtn = findViewById(R.id.pay_btn);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


    }

    void bidingAction() {
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sAmount = "100";
                int amount = Math.round(Float.parseFloat(sAmount) * 100);
                //razor check
                Checkout checkout = new Checkout();
                //set key id
                checkout.setKeyID("rzp_test_manmXiBRI19pqj");
                //set image
                checkout.setImage(R.drawable.razor_logo);
                //init  json
                JSONObject object = new JSONObject();
                try {
                    object.put("name", "ducdd");
                    object.put("description", "Payment");
                    object.put("theme.color", "#0093DD");
                    object.put("currency", "INR");
                    object.put("amount", amount);
                    object.put("prefill.contact", "9876543210");
                    object.put("prefill.email", "nguyenvanduc14012000@gmail.com");

                    //Open razor
                    checkout.open(PaymentRazorActivity.this, object);
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
        String sAmount = "100";
//        int amount=Math.round(Float.parseFloat(sAmount)*100);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment Id");
        builder.setMessage(s);
        builder.show();
        List<Order> list = (ArrayList<Order>) getIntent().getSerializableExtra("itemListA");
        if (list != null) {


            for (Order o : list) {
                HashMap<String, Object> cartMap = new HashMap<>();

                String uId = auth.getCurrentUser().getUid();
                String orderId = UUID.randomUUID().toString();
                cartMap.put("userId", uId);
                cartMap.put("orderId", orderId);
                cartMap.put("productName", o.getProductName());
                cartMap.put("quantity", o.getTotalQuantity());
                cartMap.put("currentDate", o.getCurrentDate());
                String final_address = (String) getIntent().getSerializableExtra("addressShipping");
                cartMap.put("addressShipping",final_address);
                cartMap.put("totalPrice", o.getTotalPrice());
                OrderActivity or = new OrderActivity();

                cartMap.put("status", "Payment Successfully");
                firestore.collection("Order").
                        add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(PaymentRazorActivity.this, "Payment successfull", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(PaymentRazorActivity.this, HomePageActivity.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(PaymentRazorActivity.this, "Payment fail", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }

        } else {
            Intent intent = new Intent(PaymentRazorActivity.this, HomePageActivity.class);
            Toast.makeText(PaymentRazorActivity.this, "Payment fail", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }


    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}