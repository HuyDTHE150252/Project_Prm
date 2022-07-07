package com.fptu.android.project.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.OrderDetailActivity;
import com.fptu.android.project.model.Order;
import com.fptu.android.project.service.MyForegroundService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {
    Context context;
    List<Order> orderList;


    public OrderHistoryAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;

    }
    public OrderHistoryAdapter(Context context) {
        this.context = context;


    }
    public void setData(List<Order> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_item, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Order order= orderList.get(position);
        if(order==null){
            return;
        }
        holder.name.setText(orderList.get(position).getProductName());
        holder.currentD.setText(orderList.get(position).getCurrentDate());
        holder.addressOrder.setText("1");
        holder.orderID.setText(orderList.get(position).getDocumentId());
//        holder.btnDetailOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    Intent intent= new Intent(context,OrderDetailActivity.class);
//                    intent.putExtra("detailed",order);
//                    context.startActivity(intent);
//            }
//        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MyForegroundService.class);
                i.putExtra("data","Detail");
                ContextCompat.startForegroundService(context,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, currentD,addressOrder,orderID;
        Button btnDetailOrder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderID=itemView.findViewById(R.id.order_history_item_id);
            name = itemView.findViewById(R.id.order_history_item_name);
            currentD = itemView.findViewById(R.id.order_history_item_order_date);
            addressOrder=itemView.findViewById(R.id.order_history_item_address);
            btnDetailOrder=itemView.findViewById(R.id.btndetailORDER);
        }
    }
}
