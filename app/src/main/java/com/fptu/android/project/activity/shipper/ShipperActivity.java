package com.fptu.android.project.activity.shipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.HomePageActivity;
import com.fptu.android.project.activity.user.LoginActivity;
import com.fptu.android.project.adapter.OrderHistoryAdapter;
import com.fptu.android.project.adapter.ShipperApdapter;
import com.fptu.android.project.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class ShipperActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ShipperApdapter orderHistoryAdapter;
    List<Order> orderList;
    FirebaseAuth auth;
    FirebaseFirestore db;
    Order orderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recycleListShipper);
        orderList = new ArrayList<>();
        orderHistoryAdapter = new ShipperApdapter(getApplicationContext(),orderList);
//        orderHistoryAdapter.setData(getListOrder());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(orderHistoryAdapter);
        loadAllOrder();
        if(orderList.size()==0){
            Toast.makeText(ShipperActivity.this, "List is empty", Toast.LENGTH_SHORT).show();
        }

    }

//    private List<Order> getListOrder() {
//        orderList.add(new Order("1"));
//        orderList.add(new Order("2"));
//        return orderList;
//    }

    private void loadAllOrder() {
        db.collection("Order").orderBy("currentDateOrder", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
//                               String currentTime=documentSnapshot.getString("currentDateOrder");
                                String docId = documentSnapshot.getId();
                                String address=documentSnapshot.getString("addressShipping");
                                String currentDate=documentSnapshot.getString("currentDateOrder");
                                String status=documentSnapshot.getString("orderStatus");
//                               String currentTime=documentSnapshot.getString("currentDateOrder");
                                if(status.equalsIgnoreCase("In Progress")){
                                    orderViewModel = documentSnapshot.toObject(Order.class);
                                    orderViewModel.setDocumentId(docId);
                                    orderViewModel.setAddress(address);
                                    orderViewModel.setCurrentDate(currentDate);
//                                orderViewModel.setOrderStatus(status);
                                    orderList.add(orderViewModel);
                                    orderHistoryAdapter.notifyDataSetChanged();
                                    Log.d("data", "DocumentSnapshot data: " + documentSnapshot.getId());
                                }

                            }

                        } else {
                            Log.w("err", "Error getting documents.", task.getException());
                        }
                    }
                });

//    }

    }
}