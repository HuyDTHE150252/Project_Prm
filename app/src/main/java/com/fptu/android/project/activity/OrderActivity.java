package com.fptu.android.project.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fptu.android.project.R;
import com.fptu.android.project.common.Config;
import com.fptu.android.project.model.Order;
import com.fptu.android.project.service.CartService;
import com.fptu.android.project.service.MyForegroundService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class OrderActivity extends AppCompatActivity {

    EditText fullname, phone, shippingAddress;
    Button btnConfirmAddress, btnBack,btnPaymentDirect;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    CartService cartService;



    void bidingView() {
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        fullname = findViewById(R.id.edtShipName);
        phone = findViewById(R.id.edtShipPhone);
        shippingAddress = findViewById(R.id.edtShipAddress);
        btnConfirmAddress = findViewById(R.id.btnConfirmAddress);
        btnBack = findViewById(R.id.btnBack);
        btnPaymentDirect=findViewById(R.id.btnPaymentOnline);
    }

  public String finalAddress(){
      String fullName = fullname.getText().toString();
      String addressT = shippingAddress.getText().toString();
      String phoneT = phone.getText().toString();
      String final_address="";
      if (!fullName.isEmpty()) {
          final_address += fullName + " ";
      }
      if (!phoneT.isEmpty()) {
          final_address += phoneT + " ";
      }
      if (!addressT.isEmpty()) {
          final_address += addressT + " ";
      }

      if (!fullName.isEmpty() && !addressT.isEmpty() && !phoneT.isEmpty()) {
          List<Order> list = (ArrayList<Order>) getIntent().getSerializableExtra("itemList");

          if(list!=null){
              Intent intent = new Intent(OrderActivity.this, PaymentRazorActivity.class);
              intent.putExtra("itemListA", (Serializable) list);
              intent.putExtra("addressShipping", final_address);
              startActivity(intent);

          }else{
              Intent intent = new Intent(OrderActivity.this, HomePageActivity.class);
//              intent.putExtra("itemListA", (Serializable) list);
              startActivity(intent);
          }



      } else {
          Toast.makeText(OrderActivity.this, "Please input all information to checkout", Toast.LENGTH_SHORT).show();
      }

      return  final_address;
  }

    void bidingAction() {
        btnBack.setOnClickListener(this::backScreen);
        btnConfirmAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finalAddress();
            }


        });
        btnPaymentDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = fullname.getText().toString();
                String addressT = shippingAddress.getText().toString();
                String phoneT = phone.getText().toString();
                String final_address="";
                if (!fullName.isEmpty()) {
                    final_address += fullName + " ";
                }
                if (!phoneT.isEmpty()) {
                    final_address += phoneT + " ";
                }
                if (!addressT.isEmpty()) {
                    final_address += addressT + " ";
                }

                if (!fullName.isEmpty() && !addressT.isEmpty() && !phoneT.isEmpty()) {
                    List<Order> list = (ArrayList<Order>) getIntent().getSerializableExtra("itemList");

                    if(list!=null){

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

                            cartMap.put("addressShipping", final_address);
                            cartMap.put("orderStatus","In Progress");

                            firestore
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
                                                firestore
                                                        .collection("Order").document(orderId).collection("Items").add(cap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                Toast.makeText(OrderActivity.this, "Payment successfull", Toast.LENGTH_SHORT).show();
                                                                Intent i = new Intent(OrderActivity.this, MyForegroundService.class);
                                                                i.putExtra("data","OrderId "+orderId);
                                                                ContextCompat.startForegroundService(OrderActivity.this,i);
                                                                Intent intent = new Intent( OrderActivity.this,HomePageActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                                //clean cart
                                                                cartService= new CartService(getApplicationContext());
                                                                cartService.cleanCart();
                                                            }
                                                        });
                                            }
                                        }
                                    });


                        }else{
                        Intent intent = new Intent(OrderActivity.this, HomePageActivity.class);
//              intent.putExtra("itemListA", (Serializable) list);
                        startActivity(intent);
                    }



                } else {
                    Toast.makeText(OrderActivity.this, "Please input all information to checkout", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }




    private void backScreen(View view) {
        onBackPressed();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipping_address);
        bidingView();
        bidingAction();




    }
}
