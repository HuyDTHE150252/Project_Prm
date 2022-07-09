package com.fptu.android.project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fptu.android.project.R;
import com.fptu.android.project.adapter.OrderHistoryAdapter;
import com.fptu.android.project.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderHistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    OrderHistoryAdapter orderHistoryAdapter;
    List<Order> orderList;
    FirebaseAuth auth;
    FirebaseFirestore db;
    Order orderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        recyclerView=findViewById(R.id.orderHisRecyclerView);
        orderList= new ArrayList<>();
        orderHistoryAdapter= new OrderHistoryAdapter(this,orderList);
//        orderHistoryAdapter.setData(getListOrder());
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(orderHistoryAdapter);
        loadListMyOrder();

        //loadAllOrder();

    }



    private void loadListMyOrder(){
        String uid=auth.getCurrentUser().getUid();
        db.collection("Order").whereEqualTo("userId",uid)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                               String docId = documentSnapshot.getId();
                               String address=documentSnapshot.getString("addressShipping");
                               String currentDate=documentSnapshot.getString("currentDateOrder");
                               String status=documentSnapshot.getString("orderStatus");
//                               String currentTime=documentSnapshot.getString("currentDateOrder");
                                orderViewModel = documentSnapshot.toObject(Order.class);
                                orderViewModel.setDocumentId(docId);
                                orderViewModel.setAddress(address);
                                orderViewModel.setCurrentDate(currentDate);
                                orderViewModel.setOrderStatus(status);
                                orderList.add(orderViewModel);
                                orderHistoryAdapter.notifyDataSetChanged();
//                                recyclerView.setVisibility(View.VISIBLE);
                                Log.d("t", "DocumentSnapshot data: " + documentSnapshot.getData());
                            }

                        } else {
                            Log.w("err", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

}