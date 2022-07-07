package com.fptu.android.project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.user.LoginActivity;
import com.fptu.android.project.adapter.FeedbackAdapter;
import com.fptu.android.project.model.Feedback;
import com.fptu.android.project.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView img, plus, minus;
    int quantity = 1;
    int totalPrice = 0;
    RatingBar ratingBar;
    TextView tvProductName, tvQuantity, tvPrice, tvProductAddress, feedback;
    FirebaseFirestore firestore;
    TextView btnAddToCart;
    FirebaseAuth auth;
    int cost = 0;
    Product product = null;


    void bidingView() {
        feedback = findViewById(R.id.productdetail_feedback);
        img = findViewById(R.id.productdetail_image);
        plus = findViewById(R.id.plusCardBtn);
        minus = findViewById(R.id.minusCardBtn);
        tvProductName = findViewById(R.id.productdetail_name);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvPrice = findViewById(R.id.txtPriceNumber);
        tvProductAddress = findViewById(R.id.productdetail_description);
//        checkout=findViewById(R.id.productdetail_checkout);
        btnAddToCart = findViewById(R.id.productdetail_addtocart);
        ratingBar = (RatingBar) findViewById(R.id.productdetail_rating);

    }

    void bidingAction() {
        feedback.setOnClickListener(this::feedbackProduct);
        btnAddToCart.setOnClickListener(this::onClick);
        minus.setOnClickListener(this::minusQuantity);
        plus.setOnClickListener(this::plusQuantity);
//        checkout.setOnClickListener(this::checkOut);
    }

    private void feedbackProduct(View view) {
        showRatingDialog();
    }

    private void showRatingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailActivity.this);
        builder.setTitle("Rating food")
                .setMessage("Nice")
        ;
        View itemView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_rate_product_dialog, null);
        builder.setView(itemView);
        builder.setNegativeButton("Cancael", (dialogInterface, i) ->
        {
            dialogInterface.dismiss();
        });
        builder.setPositiveButton("Ok", (dialogInterface, i) -> {


        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void plusQuantity(View view) {

        product = (Product) getIntent().getSerializableExtra("detailed");

        int currentQuantity = product.getQuantity();
        if (quantity < currentQuantity) {
            quantity++;
            tvQuantity.setText(String.valueOf(quantity));
            totalPrice = Integer.parseInt(tvQuantity.getText().toString()) * (product.getPrice());
            tvPrice.setText(String.valueOf(totalPrice));
            Toast.makeText(ProductDetailActivity.this, "Nice", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(ProductDetailActivity.this, "Max ", Toast.LENGTH_SHORT).show();
        }



    }

    private void minusQuantity(View view) {

        product = (Product) getIntent().getSerializableExtra("detailed");
        if (quantity > 1) {

            quantity--;
            tvQuantity.setText(String.valueOf(quantity));
            totalPrice = Integer.parseInt(tvQuantity.getText().toString()) * (product.getPrice());
            tvPrice.setText(String.valueOf(totalPrice));

        }


    }

    private RecyclerView rcvFeedback;
    private FeedbackAdapter feedbackadapter;

    private void bindingView() {
        rcvFeedback = findViewById(R.id.rcvFeedback);
        feedbackadapter = new FeedbackAdapter(this);
    }

    private List<Feedback> getListFeedback() {
        List<Feedback> list = new ArrayList<>();
        list.add(new Feedback("abc123", "Ngon", 5, "29/06/2022"));
        list.add(new Feedback("abc1234", "Ngon", 5, "29/06/2022"));
        list.add(new Feedback("abc12345", "Ngon", 5, "29/06/2022"));
        list.add(new Feedback("abc123456", "Ngon", 5, "29/06/2022"));
        list.add(new Feedback("abc1234567", "Ngon", 5, "29/06/2022"));

        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        bindingView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvFeedback.setLayoutManager(linearLayoutManager);
        feedbackadapter.setData(getListFeedback());
        rcvFeedback.setAdapter(feedbackadapter);
        bidingView();
        bidingAction();
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        Product product = (Product) getIntent().getSerializableExtra("detailed");
        tvProductName.setText(product.getProduct_name());
        tvProductAddress.setText(product.getProduct_address());
        img.setImageResource(product.getProduct_image());
        tvPrice.setText(String.valueOf(product.getPrice()));


//        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//                feedback.setText("Rate: "+ratingBar);
//            }
//        });


//        tvQuantity.setText(String.valueOf(product.getQuantity()));


    }

    public void addToCart() {
        product = (Product) getIntent().getSerializableExtra("detailed");
        totalPrice = Integer.parseInt(tvQuantity.getText().toString()) * (product.getPrice());

        HashMap<String, Object> cartMap = new HashMap<>();
        String uId = auth.getCurrentUser().getUid();
        cartMap.put("userId", uId);
        cartMap.put("productName", tvProductName.getText().toString());
        cartMap.put("quantity", tvQuantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);
        if (quantity == 0) {
            Toast.makeText(ProductDetailActivity.this, "Nothing add  to cart", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            firestore.collection("AddToCart").document(auth.getCurrentUser().getUid()).collection("CurrentUser")
                    .add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(ProductDetailActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
        }


    }

    private void onClick(View view) {
        if (auth.getCurrentUser() != null) {
            addToCart();
        } else {
            Intent intent = new Intent(ProductDetailActivity.this, LoginActivity.class);
            Toast.makeText(ProductDetailActivity.this, "Login first then Add to cart", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }

    }
}