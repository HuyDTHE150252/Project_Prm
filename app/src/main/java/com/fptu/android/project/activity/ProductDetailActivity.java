package com.fptu.android.project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fptu.android.project.R;
import com.fptu.android.project.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView img,plus,minus;
    int quantity=1;
    int totalPrice=0;
    TextView tvProductName, tvQuantity, tvPrice, tvProductAddress;
    FirebaseFirestore firestore;
    TextView btnAddToCart;
    FirebaseAuth auth;



    void bidingView() {
        img = findViewById(R.id.productdetail_image);
        plus = findViewById(R.id.plusCardBtn);
        minus = findViewById(R.id.minusCardBtn);
        tvProductName = findViewById(R.id.productdetail_name);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvPrice = findViewById(R.id.txtPriceNumber);
        tvProductAddress = findViewById(R.id.productdetail_description);
        btnAddToCart=findViewById(R.id.productdetail_addtocart);
    }

    void bidingAction() {
        btnAddToCart.setOnClickListener(this::onClick);
        minus.setOnClickListener(this::minusQuantity);
        plus.setOnClickListener(this::plusQuantity);
    }

    private void plusQuantity(View view) {
        Product product = (Product) getIntent().getSerializableExtra("detailed");
        int currentQuantity=product.getQuantity();
        if(quantity<currentQuantity){
            quantity++;
            tvQuantity.setText(String.valueOf(quantity));
        }
        Toast.makeText(ProductDetailActivity.this,"Nice",Toast.LENGTH_SHORT).show();
    }

    private void minusQuantity(View view) {
        if(quantity>1){
            quantity--;
            tvQuantity.setText(String.valueOf(quantity));
        }
        Toast.makeText(ProductDetailActivity.this,"Plus ik",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        bidingView();
        bidingAction();
        firestore = FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        Product product = (Product) getIntent().getSerializableExtra("detailed");
        tvProductName.setText(product.getProduct_name());
        tvProductAddress.setText(product.getProduct_address());
        img.setImageResource(product.getProduct_image());
        tvPrice.setText(String.valueOf(product.getPrice()));


//        tvQuantity.setText(String.valueOf(product.getQuantity()));



    }
    public void addToCart() {
        Product product = (Product) getIntent().getSerializableExtra("detailed");
        totalPrice=Integer.parseInt(tvQuantity.getText().toString())*(product.getPrice());
        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate= new SimpleDateFormat("MM dd,yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentDate.format(calForDate.getTime());
        HashMap<String,Object> cartMap =new HashMap<>();
        cartMap.put("productName",tvProductName.getText().toString());
        cartMap.put("quantity",tvQuantity.getText().toString());
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("totalPrice",totalPrice);


        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser")
                .add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(ProductDetailActivity.this,"Added to cart",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

    }

    private void onClick(View view) {
        addToCart();
    }
}