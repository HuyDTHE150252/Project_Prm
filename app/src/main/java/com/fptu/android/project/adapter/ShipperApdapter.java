package com.fptu.android.project.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.RateProductActivity;
import com.fptu.android.project.activity.restaurant.RestaurantCrudActivity;
import com.fptu.android.project.model.Order;
import com.fptu.android.project.service.NotificationService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ShipperApdapter extends RecyclerView.Adapter<ShipperApdapter.ViewHolder> {
    Context context;
    List<Order> orderList;


    public ShipperApdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;

    }
    public ShipperApdapter(Context context) {
        this.context = context;


    }
    public void setData(List<Order> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShipperApdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_order_history_shipper, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ShipperApdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        //holder.name.setText(orderList.get(position).getProductName());
        holder.currentD.setText(orderList.get(position).getCurrentDate());
        holder.addressOrder.setText(orderList.get(position).getAddress());
        holder.orderID.setText(orderList.get(position).getDocumentId());
        holder.completed_btn.setVisibility(View.VISIBLE);
        holder.completed_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.completed_btn.setVisibility(View.GONE);
                holder.stick.setVisibility(View.VISIBLE);
                changeStatus(position);

            }
        });
    }

    private void changeStatus(int pos) {
        Order o= orderList.get(pos);
        String status="Completed";
        String orderId=o.getDocumentId();
        FirebaseFirestore firestore= FirebaseFirestore.getInstance();
        firestore.collection("Order").document(orderId).update("orderStatus",status)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();
                        }else {

                        }Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, currentD,addressOrder,orderID;
        Button completed_btn, delete_btn;
        ImageView stick;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderID=itemView.findViewById(R.id.order_history_item_id);
//            name = itemView.findViewById(R.id.order_history_item_name);
            currentD = itemView.findViewById(R.id.order_history_item_order_date);
            addressOrder=itemView.findViewById(R.id.order_history_item_address);
            completed_btn = itemView.findViewById(R.id.completed_btn);
            stick = itemView.findViewById(R.id.tickIMG);




        }
    }
}
