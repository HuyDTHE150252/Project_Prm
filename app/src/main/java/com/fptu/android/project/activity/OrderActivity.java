package com.fptu.android.project.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    EditText fullname, phone, shippingAddress;
    Button btnConfirmAddress, btnBack;

    FirebaseAuth auth;
    FirebaseFirestore firestore;



    void bidingView() {

        fullname = findViewById(R.id.edtShipName);
        phone = findViewById(R.id.edtShipPhone);
        shippingAddress = findViewById(R.id.edtShipAddress);
        btnConfirmAddress = findViewById(R.id.btnConfirmAddress);
        btnBack = findViewById(R.id.btnBack);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();



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

    }



    private void backScreen(View view) {
        Intent intent = new Intent(OrderActivity.this, HomePageActivity.class);
        startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipping_address);
        bidingView();
        bidingAction();




    }
}
