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

import java.util.ArrayList;
import java.util.List;

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
        orderHistoryAdapter= new OrderHistoryAdapter(getApplicationContext(),orderList);
//        orderHistoryAdapter.setData(getListOrder());
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(orderHistoryAdapter);
        loadListOrder();

    }
    private void loadListOrder(){
        db.collection("Order")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                String docId = documentSnapshot.getId();
                                orderViewModel = documentSnapshot.toObject(Order.class);
                                orderViewModel.setDocumentId(docId);
                                orderList.add(orderViewModel);
                                orderHistoryAdapter.notifyDataSetChanged();
                                recyclerView.setVisibility(View.VISIBLE);
                            }

                        } else {
                            Log.w("err", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    private void loadAllOrder(){
        CollectionReference collectionReference= db.collection("Order");
        Query query=collectionReference.orderBy("currentDate", Query.Direction.ASCENDING);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (DocumentSnapshot doc : task.getResult()) {
                        String docId = doc.getId();
                        orderViewModel = doc.toObject(Order.class);
                        orderViewModel.setDocumentId(docId);
                        orderList.add(orderViewModel);
                        orderHistoryAdapter.notifyDataSetChanged();
                    }


                } else {
                    Log.d("err", "Error getting documents: ", task.getException());
                }
            }
        });
    }


}