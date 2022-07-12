package com.fptu.android.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fptu.android.project.R;
import com.fptu.android.project.activity.user.LoginActivity;
import com.fptu.android.project.adapter.FeedbackAdapter;
import com.fptu.android.project.model.Feedback;
import com.fptu.android.project.model.Product;
import com.fptu.android.project.model.Restaurant;
import com.fptu.android.project.service.CartService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView img, plus, minus;
    int quantity = 1;
    int totalPrice = 0;
    RatingBar ratingBar;
    TextView tvProductName, tvQuantity, tvPrice, feedback, description;
    FirebaseFirestore firestore;
    TextView btnAddToCart;
    FirebaseAuth auth;
    int cost = 0;
    Product product = null;
    List<Feedback> feedbackList;
    Feedback f;
    CartService c;
    int current = 20;


    void bidingView() {

        img = findViewById(R.id.productdetail_image);
        plus = findViewById(R.id.plusCardBtn);
        minus = findViewById(R.id.minusCardBtn);
        tvProductName = findViewById(R.id.productdetail_name);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvPrice = findViewById(R.id.txtPriceNumber);
        btnAddToCart = findViewById(R.id.productdetail_addtocart);
//        ratingBar = (RatingBar) findViewById(R.id.productdetail_rating);
        description = findViewById(R.id.productdetail_description);
    }

    void bidingAction() {

        btnAddToCart.setOnClickListener(this::onClick);
        minus.setOnClickListener(this::minusQuantity);
        plus.setOnClickListener(this::plusQuantity);

    }




    private void plusQuantity(View view) {

        product = (Product) getIntent().getSerializableExtra("detailed");


        if (quantity < current) {
            quantity++;
            tvQuantity.setText(String.valueOf(quantity));
            totalPrice = Integer.parseInt(tvQuantity.getText().toString()) * (product.getProduct_price());
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
            totalPrice = Integer.parseInt(tvQuantity.getText().toString()) * (product.getProduct_price());
            tvPrice.setText(String.valueOf(totalPrice));

        }


    }

    private RecyclerView rcvFeedback;
    private FeedbackAdapter feedbackadapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        bidingView();
        bidingAction();
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        Product product = (Product) getIntent().getSerializableExtra("detailed");
        tvProductName.setText(product.getProduct_name());
        tvPrice.setText(String.valueOf(product.getProduct_price()));
        description.setText(product.getDescription());
        Glide.with(getApplicationContext()).load(product.getProduct_url())
                .into(img);

    }
    private void onClick(View view) {
        if (auth.getCurrentUser() != null) {
            product = (Product) getIntent().getSerializableExtra("detailed");
            totalPrice = Integer.parseInt(tvQuantity.getText().toString()) * (product.getProduct_price());
            String uId = auth.getCurrentUser().getUid();
            c = new CartService(getApplicationContext());
            c.addToCart(uId, tvProductName.getText().toString(), tvQuantity.getText().toString(), totalPrice);
            Intent intent = new Intent(ProductDetailActivity.this, HomePageActivity.class);
            Toast.makeText(ProductDetailActivity.this, "Added", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        } else {
            Intent intent = new Intent(ProductDetailActivity.this, LoginActivity.class);
            Toast.makeText(ProductDetailActivity.this, "Login first then Add to cart", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }

    }

}
