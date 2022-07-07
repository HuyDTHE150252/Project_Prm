package com.fptu.android.project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.fragment.CartFragment;
import com.fptu.android.project.activity.user.LoginActivity;
import com.fptu.android.project.adapter.FeedbackAdapter;
import com.fptu.android.project.model.Feedback;
import com.fptu.android.project.model.Order;
import com.fptu.android.project.model.Product;
import com.fptu.android.project.service.MyForegroundService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.perf.util.Constants;

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
    List<Feedback> feedbackList;
    Feedback f;


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
        product = (Product) getIntent().getSerializableExtra("detailed");
          Intent intent= new Intent(ProductDetailActivity.this,RateProductActivity.class);
          intent.putExtra("ratingProduct",product);
          startActivity(intent);
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
        bidingView();
        bidingAction();
        bindingView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvFeedback.setLayoutManager(linearLayoutManager);
        feedbackList= new ArrayList<>();
        //feedbackadapter= new FeedbackAdapter(this,feedbackList);
        feedbackadapter.setData(getListFeedback());
        rcvFeedback.setAdapter(feedbackadapter);
        loadListFeedback();

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        Product product = (Product) getIntent().getSerializableExtra("detailed");
        tvProductName.setText(product.getProduct_name());
        tvProductAddress.setText(product.getProduct_address());
        img.setImageResource(product.getProduct_image());
        tvPrice.setText(String.valueOf(product.getPrice()));
//        tvQuantity.setText((String.valueOf(product.getQuantity())));
    }

    private void loadListFeedback() {
//        firestore.collection("Feedback")
//               .orderBy("currentDateOrder", Query.Direction.ASCENDING)
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
//                                String docId = documentSnapshot.getId();
//                               f= documentSnapshot.toObject(Feedback.class);
//                                f.setFeedback_id(String.valueOf(docId));
//                                feedbackList.add(f);
//                                feedbackadapter.notifyDataSetChanged();
//                                rcvFeedback.setVisibility(View.VISIBLE);
//                            }
//
//                        } else {
//                            Log.w("err", "Error getting documents.", task.getException());
//                        }
//                    }
//                });

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
                            Intent i = new Intent(ProductDetailActivity.this, MyForegroundService.class);
                            i.putExtra("data","Item added to cart");
                            ContextCompat.startForegroundService(ProductDetailActivity.this,i);
                            Log.d("t", "db rating getString() is: " + task.getResult());
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