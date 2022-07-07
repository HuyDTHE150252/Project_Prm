package com.fptu.android.project.adapter;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.EmptyCartActivity;
import com.fptu.android.project.activity.HomePageActivity;
import com.fptu.android.project.activity.OrderActivity;
import com.fptu.android.project.activity.PaymentRazorActivity;
import com.fptu.android.project.activity.fragment.CartFragment;
import com.fptu.android.project.model.Order;
import com.fptu.android.project.model.Product;
import com.fptu.android.project.service.MyForegroundService;
import com.fptu.android.project.service.NotificationService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.io.Serializable;
import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    Context context;
    List<Order> cartList;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public MyCartAdapter(Context context, List<Order> cartList) {
        this.context = context;
        this.cartList = cartList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public void setData(List<Order> cartList) {
        this.cartList = cartList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(cartList.get(position).getProductName());
        holder.txtquantity.setText(cartList.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(cartList.get(position).getTotalPrice()));
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItemCart(position);

            }
        });





    }
    public void cleanCart(){
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
                                    Toast.makeText(context, "J: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Toast.makeText(context, "No data in J", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(context, "Jtask: " + task.getException(), Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error getting all the documents: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void deleteItemCart(int position){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Delete");
        alert.setMessage("Are you sure you want to delete?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                        .collection("CurrentUser")
                        .document(cartList.get(position).getDocumentId())
                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    cartList.remove(cartList.get(position));
                                    notifyItemChanged(position);
                                    notifyDataSetChanged();
                                    Intent i = new Intent(context, MyForegroundService.class);
                                    i.putExtra("data","Item Deleted");
                                    ContextCompat.startForegroundService(context,i);

//                                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, txtquantity, totalPrice;
        ImageView deleteItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cart_product_name);
            totalPrice = itemView.findViewById(R.id.cart_totalPrice);
            txtquantity = itemView.findViewById(R.id.cart_totalQuantity);
            deleteItem = itemView.findViewById(R.id.imgDeleteItem);



        }
    }

}
