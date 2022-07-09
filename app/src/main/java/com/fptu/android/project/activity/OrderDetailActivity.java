package com.fptu.android.project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.fptu.android.project.R;
import com.fptu.android.project.adapter.OrderDetailAdapter;
import com.fptu.android.project.adapter.OrderHistoryAdapter;
import com.fptu.android.project.model.Order;
import com.fptu.android.project.service.CartService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    RecyclerView recyclerView;
    OrderDetailAdapter orderDetailAdapter;
    List<Order> orderList;
    Order orderViewModel;
    TextView tvOrderId,tvorderDate,orderAddress,tvStatus,totalPrice;
    void bidingView(){
        tvOrderId=findViewById(R.id.textViewOrderID);
        tvorderDate=findViewById(R.id.textViewOrderDate);
        orderAddress=findViewById(R.id.textViewAddress);
        tvStatus=findViewById(R.id.textViewStatus);
        totalPrice=findViewById(R.id.textViewTotalPrice);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }
    void bidingAction(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        bidingView();
        bidingAction();
        Intent intent=getIntent();
        Bundle b= intent.getExtras();
        if(b!=null){
            String orderId=b.getString("orderId");
            String orderDate=b.getString("orderDate");
            String address=b.getString("orderAddress");
            String status=b.getString("orderStatus");
            tvOrderId.setText(orderId);
            tvorderDate.setText(orderDate);
            orderAddress.setText(address);
            tvStatus.setText(status);
        }
        recyclerView=findViewById(R.id.order_detail_recycler_view);
        orderList= new ArrayList<>();
        orderDetailAdapter= new OrderDetailAdapter(this,orderList);
//        orderHistoryAdapter.setData(getListOrder());
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(orderDetailAdapter);
        loadListMyItem();



    }

    private void loadListMyItem() {
        Intent intent=getIntent();
        Bundle b= intent.getExtras();
        String orderId=b.getString("orderId");

        firestore.collection("CurrentUserOrder").document(auth.getCurrentUser().getUid())
                .collection("Order").document(orderId).collection("Items")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                String status=b.getString("orderStatus");
                                String name=documentSnapshot.getString("productName");
                                String quantity=documentSnapshot.getString("quantity");
                                String totalP=documentSnapshot.getString("totalP");

//                               String currentTime=documentSnapshot.getString("currentDateOrder");
                               orderViewModel = documentSnapshot.toObject(Order.class);
                               orderViewModel.setTotalQuantity(quantity);
                               orderViewModel.setProductName(name);
                               orderViewModel.setTotalPrice(Integer.parseInt(totalP));
                               orderViewModel.setOrderStatus(status);

                                orderList.add(orderViewModel);
                                orderDetailAdapter.notifyDataSetChanged();
//                                recyclerView.setVisibility(View.VISIBLE);
                                Log.d("d", "DocumentSnapshot data: " + documentSnapshot.getId());
                            }
                            calculateTotalAmount(orderList);

                        } else {
                            Log.w("err", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    public void calculateTotalAmount(List<Order> cartList) {

        double totalAmount = 0.0;
        for (Order o : cartList) {
            totalAmount += o.getTotalPrice();
            totalPrice.setText("" + totalAmount);
        }

    }
}