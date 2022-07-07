package com.fptu.android.project.activity.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.EmptyCartActivity;
import com.fptu.android.project.activity.OrderActivity;
import com.fptu.android.project.activity.PaymentRazorActivity;
import com.fptu.android.project.adapter.MyCartAdapter;
import com.fptu.android.project.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    MyCartAdapter cartAdapter;
    List<Order> cartList;
    FirebaseAuth auth;
    FirebaseFirestore db;
    Order cartViewModel;
    TextView overTotalAmount;
    TextView tvCheckout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.cart_recycle_view);
        tvCheckout = view.findViewById(R.id.cart_checkout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(getActivity(), cartList);
        overTotalAmount = view.findViewById(R.id.totalTxt);

        tvCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartList.size() > 0) {
                    Intent intent = new Intent(getContext(), OrderActivity.class);
                    intent.putExtra("itemList", (Serializable) cartList);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Your cart is empty", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), EmptyCartActivity.class);
                    startActivity(intent);
                }


            }
        });
        getAllListProductCart();
        recyclerView.setAdapter(cartAdapter);
        // cart


        return view;
    }

    private void getAllListProductCart() {
        db.collection("AddToCart").document(auth.getCurrentUser().getUid()).collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                                String docId = documentSnapshot.getId();
                                cartViewModel = documentSnapshot.toObject(Order.class);
                                cartViewModel.setDocumentId(docId);
                                cartList.add(cartViewModel);
                                cartAdapter.notifyDataSetChanged();
                                recyclerView.setVisibility(View.VISIBLE);



                                Log.d("Write1", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                            }
                            calculateTotalAmount(cartList);

                        } else {
                            Log.w("Write1", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void calculateTotalAmount(List<Order> cartList) {

        double totalAmount = 0.0;
        for (Order o : cartList) {
            totalAmount += o.getTotalPrice();
            overTotalAmount.setText("" + totalAmount);
        }

    }


}