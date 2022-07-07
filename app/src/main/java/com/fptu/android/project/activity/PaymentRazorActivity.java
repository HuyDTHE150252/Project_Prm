package com.fptu.android.project.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fptu.android.project.R;
import com.fptu.android.project.adapter.MyCartAdapter;
import com.fptu.android.project.model.Order;
import com.fptu.android.project.service.CartService;
import com.fptu.android.project.service.MyForegroundService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class PaymentRazorActivity extends AppCompatActivity implements PaymentResultListener {
    TextView totalPrice;
    Button payBtn;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    CartService cartService;
    List<Order> list;

    private void bidingView() {
        payBtn = findViewById(R.id.pay_btn);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        totalPrice = findViewById(R.id.total_amt);


    }

    void bidingAction() {

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amount=0;
               list = (ArrayList<Order>) getIntent().getSerializableExtra("itemListA");
                for (int i=0;i<list.size();i++){
                    amount+=list.get(i).getTotalPrice();
                }

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
                    object.put("amount", amount*100);
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
        int amount=0;
        list = (ArrayList<Order>) getIntent().getSerializableExtra("itemListA");
        for (int i=0;i<list.size();i++){
            amount+=list.get(i).getTotalPrice();
        }
        totalPrice.setText(String.valueOf(amount));
        bidingAction();


    }



    @Override
    public void onPaymentSuccess(String s) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment Id");
        builder.setMessage(s);
        builder.show();

        //notification


       list = (ArrayList<Order>) getIntent().getSerializableExtra("itemListA");
        if (list != null) {
            HashMap<String, Object> cartMap = new HashMap<>();
            String uId = auth.getCurrentUser().getUid();
            String orderId = UUID.randomUUID().toString();

            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,yyyy");
            String saveCurrentDate = currentDate.format(calForDate.getTime());
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            String saveCurrentTime = currentTime.format(calForDate.getTime());
            cartMap.put("userId", uId);
            cartMap.put("currentTimeOrder", saveCurrentTime);
            cartMap.put("currentDateOrder", saveCurrentDate);
            String final_address = (String) getIntent().getSerializableExtra("addressShipping");
            cartMap.put("addressShipping", final_address);
            cartMap.put("status", "Payment Successfully");
            firestore.collection("CurrentUserOrder").document(auth.getCurrentUser().getUid())
                    .collection("Order").document(orderId)
                    .set(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            for (int i = 0; i < list.size(); i++) {
                                String name = list.get(i).getProductName();
                                String quantity = list.get(i).getTotalQuantity();
                                String totalP = String.valueOf(list.get(i).getTotalPrice());
                                HashMap<String, String> cap = new HashMap<>();
                                cap.put("productName", name);
                                cap.put("quantity", quantity);
                                cap.put("totalP", totalP);
                                firestore.collection("CurrentUserOrder").document(auth.getCurrentUser().getUid())
                                        .collection("Order").document(orderId).collection("Items").add(cap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                Toast.makeText(PaymentRazorActivity.this, "Payment successfull", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(PaymentRazorActivity.this, MyForegroundService.class);
                                                i.putExtra("data","OrderId "+orderId);
                                                ContextCompat.startForegroundService(PaymentRazorActivity.this,i);
                                                startActivity(new Intent(PaymentRazorActivity.this,HomePageActivity.class));
                                                finish();
                                                //clean cart
                                                MyCartAdapter m = new MyCartAdapter(getApplicationContext(), list);
                                                m.cleanCart();
                                            }
                                        });
                            }
                        }
                    });


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