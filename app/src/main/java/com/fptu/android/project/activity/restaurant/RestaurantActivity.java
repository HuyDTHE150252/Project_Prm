package com.fptu.android.project.activity.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.RatingRestaurantActivity;
import com.fptu.android.project.adapter.FeedbackAdapter;
import com.fptu.android.project.model.Feedback;
import com.fptu.android.project.model.Product;
import com.fptu.android.project.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {

    List<Feedback> feedbackList;
    private RecyclerView rcvFeedback;
    private FeedbackAdapter feedbackadapter;
    TextView tvFeedback;
    TextView feedbackUserName;
    TextView des,dateFeedback;
    RatingBar ratingBar;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    RatingBar ratingRes;
    float sum=0;
    int count =0;

    private void bindingView() {
        feedbackUserName=findViewById(R.id.feedback_username);
        des=findViewById(R.id.feedback_description);
        dateFeedback=findViewById(R.id.feedback_date);
        rcvFeedback = findViewById(R.id.rcvFeedbackApp);
        feedbackadapter = new FeedbackAdapter(this);
        tvFeedback=findViewById(R.id.tvFeedback);
        ratingBar=findViewById(R.id.feedback_rating);
        auth=FirebaseAuth.getInstance();
        ratingRes=findViewById(R.id.res_rating);
        firestore=FirebaseFirestore.getInstance();

    }
    private void bindingAction(){
        tvFeedback.setOnClickListener(this::feedbackApp);
    }

    private void feedbackApp(View view) {
        startActivity(new Intent(RestaurantActivity.this, RatingRestaurantActivity.class));
    }

    private List<Feedback> getListFeedback() {
        List<Feedback> list = new ArrayList<>();
        firestore.collection("Feedback")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (DocumentSnapshot document: task.getResult()) {
                                if (document.exists()) {
                                    Feedback feedback = document.toObject(Feedback.class);
                                    String username=document.getString("username");
                                    String rating = document.getString("rating");
                                    feedback.setRate(Float.valueOf(rating.toString()));
                                    String review = document.getString("review");
                                    feedback.setDescription(review);
                                    String dateF = document.getString("currentDateFeedback");
                                    feedback.setFeedback_date(dateF);
                                    feedback.setUser_name(username);
                                    Toast.makeText(RestaurantActivity.this, "OK", Toast.LENGTH_SHORT).show();
                                    list.add(feedback);
                                    feedbackadapter.notifyDataSetChanged();
                                    Log.d("t", "DocumentSnapshot data: " + document.getData());
                                    Log.d("t", "db rating getString() is: " + document.getString("rating"));
                                    Log.d("t", "db rating getString() is: " + document.getString("review"));

                                    sum+=Float.parseFloat(String.valueOf(feedback.getRate()));
                                    count++;


                                } else {
                                    Toast.makeText(RestaurantActivity.this, "Nice", Toast.LENGTH_SHORT).show();
                                }

                            }
                            float avr=sum/count;
                            ratingRes.setRating(avr);
                        }
                    }
                });
        return list;
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
        feedbackadapter.setData(getListFeedback());
        rcvFeedback.setAdapter(feedbackadapter);




    }
}