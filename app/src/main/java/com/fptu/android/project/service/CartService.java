package com.fptu.android.project.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fptu.android.project.activity.ProductDetailActivity;
import com.fptu.android.project.interfaces.CartInterface;
import com.fptu.android.project.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.List;

public class CartService implements CartInterface {
    FirebaseFirestore firestore;
    List<Order> cartList;
    FirebaseAuth auth;
    Context context;

    public CartService(Context context) {
        this.context=context;
    }

    @Override
    public void addToCart() {

    }

    @Override
    public void cleanCart() {
        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid()).collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (final QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            firestore.collection("AddToCart").document(auth.getCurrentUser().getUid()).collection("CurrentUser").document(document.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Journal: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Toast.makeText(context, "No data in JOURNAL", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(context, "Journal task: " + task.getException(), Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error getting all the documents: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void deleteItemCart() {

    }
}


