package com.fptu.android.project.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.ProductDetailActivity;
import com.fptu.android.project.model.MyCart;
import com.fptu.android.project.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    Context context;
    List<MyCart> cartList;
    int totalPrice = 0;
    FirebaseFirestore firestore;

    FirebaseAuth auth;

    public MyCartAdapter(Context context, List<MyCart> cartList) {
        this.context = context;
        this.cartList = cartList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public void setData(List<MyCart> cartList) {
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

        holder.currentT.setText(cartList.get(position).getCurrentTime());
        holder.currentD.setText(cartList.get(position).getCurrentDate());
        holder.quantity.setText(cartList.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(cartList.get(position).getTotalPrice()));
        totalPrice = totalPrice + cartList.get(position).getTotalPrice();
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                            notifyDataSetChanged();
                                            Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
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
        });



        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantity, currentT, currentD, totalPrice;
        ImageView deleteItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cart_product_name);
            totalPrice = itemView.findViewById(R.id.cart_totalPrice);
            quantity = itemView.findViewById(R.id.cart_totalQuantity);
            currentD = itemView.findViewById(R.id.cart_currentdate);
            currentT = itemView.findViewById(R.id.cart_currenttime);
            deleteItem = itemView.findViewById(R.id.imgDeleteItem);
        }
    }
}
