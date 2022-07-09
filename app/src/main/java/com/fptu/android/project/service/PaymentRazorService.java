package com.fptu.android.project.service;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.fptu.android.project.activity.HomePageActivity;
import com.fptu.android.project.activity.PaymentRazorActivity;
import com.fptu.android.project.interfaces.PaymentRazorInterface;
import com.fptu.android.project.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PaymentRazorService  implements PaymentRazorInterface  {
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    CartService cartService;
    List<Order> list;
    Context context;
    public PaymentRazorService(Context context) {
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        list= new ArrayList<>();
        this.context=context;
    }

    @Override
    public void getInformPaymentByRazor(String uid, String date, String time, String address) {
//        String orderId = UUID.randomUUID().toString();
//        HashMap<String, Object> cartMap = new HashMap<>();
//        cartMap.put("userId", uid);
//        cartMap.put("currentTimeOrder", time);
//        cartMap.put("currentDateOrder", date);
//        cartMap.put("addressShipping", address);
//        cartMap.put("status", "Payment Successfully");
//        firestore.collection("CurrentUserOrder").document(auth.getCurrentUser().getUid())
//                .collection("Order").document(orderId)
//                .set(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        for (int i = 0; i < list.size(); i++) {
//                            String name = list.get(i).getProductName();
//                            String quantity = list.get(i).getTotalQuantity();
//                            String totalP = String.valueOf(list.get(i).getTotalPrice());
//                            HashMap<String, String> cap = new HashMap<>();
//                            cap.put("productName", name);
//                            cap.put("quantity", quantity);
//                            cap.put("totalP", totalP);
//                            firestore.collection("CurrentUserOrder").document(auth.getCurrentUser().getUid())
//                                    .collection("Order").document(orderId).collection("Items").add(cap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                                            Toast.makeText(context, "Payment successfull", Toast.LENGTH_SHORT).show();
//                                            Intent i = new Intent(context, MyForegroundService.class);
//                                            i.putExtra("data","OrderId "+orderId);
//                                            ContextCompat.startForegroundService(context,i);
//
//                                            //clean cart
//                                            cartService= new CartService(context);
//                                            cartService.cleanCart();
//                                        }
//                                    });
//                        }
//                    }
//                });
//
    }
}
