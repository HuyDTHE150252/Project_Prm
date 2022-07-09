package com.fptu.android.project.service;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.fptu.android.project.activity.ProductDetailActivity;
import com.fptu.android.project.adapter.MyCartAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartService implements CartInterface {
    FirebaseFirestore firestore;
    List<Order> cartList;
    FirebaseAuth auth;
    Context context;

    public CartService(Context context) {
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        this.context=context;
    }


    @Override
    public void addToCart(String uid, String name, String quantity, int total) {
        HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("userId", uid);
        cartMap.put("productName", name);
        cartMap.put("quantity", quantity);
        cartMap.put("totalPrice",total);


            firestore.collection("AddToCart").document(auth.getCurrentUser().getUid()).collection("CurrentUser")
                    .add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Intent i = new Intent(context, MyForegroundService.class);
                            i.putExtra("data","Item added to cart");
                            ContextCompat.startForegroundService(context,i);
                            Log.d("t", "db rating getString() is: " + task.getResult());


                        }
                    });

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

//    @Override
//    public void deleteItemCart(int position) {
//
//        AlertDialog.Builder alert = new AlertDialog.Builder(context);
//        alert.setTitle("Delete");
//        alert.setMessage("Are you sure you want to delete?");
//        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
//                        .collection("CurrentUser")
//                        .document(cartList.get(position).getDocumentId())
//                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//
//                                    cartList.remove(cartList.get(position));
//                                    MyCartAdapter m= new MyCartAdapter();
//                                    m.notifyItemChanged(position);
//                                    m.notifyDataSetChanged();
//                                    Intent i = new Intent(context, MyForegroundService.class);
//                                    i.putExtra("data","Item Deleted");
//                                    ContextCompat.startForegroundService(context,i);
//                                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
//
//                                } else {
//                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//
//
//
//                dialog.dismiss();
//            }
//        });
//
//        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        alert.show();
//    }


}


