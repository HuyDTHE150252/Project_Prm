package com.fptu.android.project.activity.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.RatingRestaurantActivity;
import com.fptu.android.project.adapter.FeedbackAdapter;
import com.fptu.android.project.model.Feedback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {

    List<Feedback> feedbackList;
    private RecyclerView rcvFeedback;
    private FeedbackAdapter feedbackadapter;
    TextView tvFeedback;
    TextView feedbackUserName;
    TextView des, dateFeedback;
    RatingBar ratingBar;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    RatingBar ratingRes;


    private void bindingView() {
        feedbackUserName = findViewById(R.id.feedback_username);
        des = findViewById(R.id.feedback_description);
        dateFeedback = findViewById(R.id.feedback_date);
        rcvFeedback = findViewById(R.id.rcvFeedbackApp);
        feedbackadapter = new FeedbackAdapter(this);
        tvFeedback = findViewById(R.id.tvFeedback);
        ratingBar = findViewById(R.id.feedback_rating);
        auth = FirebaseAuth.getInstance();
        ratingRes = findViewById(R.id.res_rating);
        firestore = FirebaseFirestore.getInstance();

    }

    private void bindingAction() {
        tvFeedback.setOnClickListener(this::feedbackApp);
    }

    private void feedbackApp(View view) {
        startActivity(new Intent(RestaurantActivity.this, RatingRestaurantActivity.class));
    }



    private void getListFeedback() {
        firestore.collection("Feedback").orderBy("currentDateFeedback", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    List<Feedback> list = new ArrayList<>();
                    float sum = 0;
                    int count = 0;
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                if (document.exists()) {
                                    firestore.collection("users").document(document.getString("userId"))
                                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                Feedback feedback;
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    System.out.println("data: " + documentSnapshot.getString("fName"));
                                                    feedback = new Feedback();
                                                    String rating = document.getString("rating");
                                                    feedback.setRate(Float.valueOf(rating));
                                                    String review = document.getString("review");
                                                    feedback.setDescription(review);
                                                    String dateF = document.getString("currentDateFeedback");
                                                    feedback.setFeedback_date(dateF);
                                                    feedback.setUser_name(documentSnapshot.getString("fName"));
                                                    // Toast.makeText(RestaurantActivity.this, "OK", Toast.LENGTH_SHORT).show();
                                                    list.add(feedback);
                                                    feedbackadapter.setData(list);
                                                    Log.d("t", "DocumentSnapshot data: " + document.getData());
                                                    Log.d("t", "db rating getString() is: " + document.getString("rating"));
                                                    Log.d("t", "db rating getString() is: " + document.getString("review"));

                                                    sum += Float.parseFloat(String.valueOf(feedback.getRate()));
                                                    count++;
                                                    System.out.println("sum: " + sum);
                                                    System.out.println("count: " + count);
                                                    float avr = sum / count;
                                                    ratingRes.setRating(avr);
                                                }
                                            });
                                } else {
                                    Toast.makeText(RestaurantActivity.this, "Nice", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        bindingView();
        bindingAction();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvFeedback.setLayoutManager(linearLayoutManager);
        feedbackList = new ArrayList<>();
        getListFeedback();
        rcvFeedback.setAdapter(feedbackadapter);


    }
}