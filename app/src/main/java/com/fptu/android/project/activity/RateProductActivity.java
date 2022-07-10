package com.fptu.android.project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.restaurant.RestaurantCrudActivity;
import com.fptu.android.project.model.Feedback;
import com.fptu.android.project.model.Order;
import com.fptu.android.project.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class RateProductActivity extends AppCompatActivity {
    ImageView imgRate, cancelRating;
    TextView tv;
    EditText edtComment;
    RatingBar ratingBar;
    Button btnConfirm;
    FirebaseAuth auth;
    FirebaseFirestore firestore;


    void bidingView() {
        imgRate = findViewById(R.id.rateProductImage);
        tv = findViewById(R.id.rateProductName);
        edtComment = findViewById(R.id.edtCardNumber);
        ratingBar = findViewById(R.id.rateBar);
        btnConfirm = findViewById(R.id.rateConfirmButton);
        cancelRating = findViewById(R.id.cancelRateDialog);
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
    }

    void bidingAction() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    inputData();
            }
        });
        cancelRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void inputData() {
        Product product = (Product) getIntent().getSerializableExtra("ratingProduct");
        String pname=product.getProduct_name();
        String rating=""+ratingBar.getRating();
        String review=edtComment.getText().toString();
        String timeStamp=""+System.currentTimeMillis();
        HashMap<String,Object> map= new HashMap<>();

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,yyyy");
        String saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        String saveCurrentTime = currentTime.format(calForDate.getTime());
        map.put("userId",auth.getCurrentUser().getUid());
        map.put("currentTimeFeedback", saveCurrentTime);
        map.put("currentDateFeedback", saveCurrentDate);
        map.put("rating",""+rating);
        map.put("review",""+review);
        map.put("timeStamp",timeStamp);
        map.put("productName",pname);
        firestore.collection("Feedback").document(auth.getCurrentUser().getUid())
                .set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(RateProductActivity.this, "Nice", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_product);
        bidingView();
        bidingAction();
        Product product = (Product) getIntent().getSerializableExtra("ratingProduct");
        tv.setText(product.getProduct_name());
//        imgRate.setImageResource(product.getProduct_image());
        loadMyReview();

    }

    private void loadMyReview() {
        firestore.collection("Feedback").document(auth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Log.d("t", "DocumentSnapshot data: " + document.getData());
                        Log.d("t", "db rating getString() is: " + document.getString("rating"));
                        Log.d("t", "db rating getString() is: " + document.getString("review"));

                         String rating =  document.getString("rating");

                         ratingBar.setRating(Float.parseFloat(rating));
                         String review =  document.getString("review");
                         edtComment.setText(review);
                        Log.d("t", "String start is: " +ratingBar );
                        Log.d("t", "String review is: " + edtComment);
                        Toast.makeText(RateProductActivity.this, "OK", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RateProductActivity.this, "Nice", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("T", "get failed with ", task.getException());
                }
            }
        });
    }
    }
