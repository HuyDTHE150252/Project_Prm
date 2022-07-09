package com.fptu.android.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;

import com.fptu.android.project.activity.HomePageActivity;
import com.fptu.android.project.activity.restaurant.RestaurantActivity;
import com.fptu.android.project.activity.restaurant.RestaurantDetailActivity;
import com.fptu.android.project.model.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>{

    private Context context;
    private List<Restaurant> listRestaurants;

    public RestaurantAdapter(Context context, List<Restaurant> listRestaurants) {
        this.context = context;
        this.listRestaurants=listRestaurants;
    }

    public void setData(List<Restaurant> list) {
        this.listRestaurants = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new RestaurantViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = listRestaurants.get(position);
        if(restaurant == null){
            return;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RestaurantDetailActivity.class);
                intent.putExtra("restaurant",restaurant);
                context.startActivity(intent);
            }
        });
        holder.restaurant_name.setText(restaurant.getRestaurant_name());
        holder.restaurant_address.setText(restaurant.getRestaurant_address());
        holder.restaurant_rate.setText(restaurant.getRestaurant_rate());
        holder.restaurant_status.setText(restaurant.getRestaurant_status());
    }

    @Override
    public int getItemCount() {
        return listRestaurants.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {

        private ImageView restaurant_image;
        private TextView restaurant_id;
        private TextView uid;
        private TextView restaurant_name;
        private TextView restaurant_address;

        private TextView restaurant_rate;
        private TextView restaurant_status;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurant_image = itemView.findViewById(R.id.restaurant_image);
            //restaurant_id = itemView.findViewById(R.id.restaurant_id);
            //uid = itemView.findViewById(R.id.uid);
            restaurant_name = itemView.findViewById(R.id.restaurant_name);
            restaurant_address = itemView.findViewById(R.id.restaurant_address);
            restaurant_rate = itemView.findViewById(R.id.restaurant_rate);
            restaurant_status = itemView.findViewById(R.id.restaurant_status);


        }
    }
}
