package com.fptu.android.project.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.model.Order;


import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    Context context;
    List<Order> orderList;



    public OrderDetailAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;


    }
    public OrderDetailAdapter(Context context) {
        this.context = context;


    }
    public void setData(List<Order> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderDetailAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_item, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Order order= orderList.get(position);
        if(order==null){
            return;
        }
        String orderStatus=order.getOrderStatus();

        holder.name.setText(orderList.get(position).getProductName());
        holder.quantity.setText(orderList.get(position).getTotalQuantity());
        holder.price.setText(String.valueOf(orderList.get(position).getTotalPrice()));
        holder.status.setText(orderList.get(position).getOrderStatus());
        if(orderStatus.equals("In Progress")){
            holder.status.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }else if(orderStatus.equals("Completed")){
            holder.status.setTextColor(context.getResources().getColor(R.color.md_green_900));
        }else if(orderStatus.equals("Cancel")){
            holder.status.setTextColor(context.getResources().getColor(R.color.md_deep_orange_A700));
        }

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,addressOrder,status,quantity,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quantity=itemView.findViewById(R.id.order_item_detail_quantity);
            price=itemView.findViewById(R.id.order_item_price);
            name = itemView.findViewById(R.id.order_history_item_name);
            status=itemView.findViewById(R.id.tvStatusShipping);
        }
    }
}
