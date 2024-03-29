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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.model.Order;
import com.fptu.android.project.service.CartService;
import com.fptu.android.project.service.MyForegroundService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    List<Order> cartList;
    CartService c;

    public CartAdapter() {
    }

    public CartAdapter(Context context, List<Order> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    public void setData(List<Order> cartList) {
        this.cartList = cartList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
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

    public void deleteItemCart(int position){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();;
        FirebaseAuth  auth = FirebaseAuth.getInstance();
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
