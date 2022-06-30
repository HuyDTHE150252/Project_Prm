package com.fptu.android.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.model.MyCart;
import com.fptu.android.project.model.Product;

import java.util.List;

public class MyCartAdapter  extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    Context context;
    List<MyCart> cartList;

    public MyCartAdapter(Context context, List<MyCart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }
    public void setData(List<MyCart> cartList) {
        this.cartList =cartList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item, parent, false);
        return new MyCartAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {

            holder.name.setText(cartList.get(position).getProductName());

            holder.currentT.setText(cartList.get(position).getCurrentTime());
            holder.currentD.setText(cartList.get(position).getCurrentDate());
            holder.quantity.setText(cartList.get(position).getTotalQuantity());
            holder.totalP.setText(String.valueOf(cartList.get(position).getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,quantity,currentT,currentD,totalP;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.cart_product_name);
            totalP=itemView.findViewById(R.id.cart_totalPrice);
            quantity=itemView.findViewById(R.id.cart_totalQuantity);
            currentD=itemView.findViewById(R.id.cart_currentdate);
            currentT=itemView.findViewById(R.id.cart_currenttime);
        }
    }
}
