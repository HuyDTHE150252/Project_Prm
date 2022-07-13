package com.fptu.android.project.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fptu.android.project.R;
import com.fptu.android.project.activity.ProductDetailActivity;
import com.fptu.android.project.model.Product;

import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder>{

    private Context context;
    private List<Product> listTrending;

    public TrendingAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Product> list) {
        this.listTrending = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_item, parent, false);
        return new TrendingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product trending = listTrending.get(position);
        if(trending == null){
            return;
        }
        Glide.with(context).load(listTrending.get(position).getProduct_url()).into(holder.trending_img);
        holder.trending_name.setText(trending.getProduct_name());
        holder.trend_description.setText(trending.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("detailed", listTrending.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTrending.size();
    }

    public class TrendingViewHolder extends RecyclerView.ViewHolder {

        private ImageView trending_img;
        private TextView trending_name;
        private TextView trend_description;

        public TrendingViewHolder(@NonNull View itemView) {
            super(itemView);
            trending_img = itemView.findViewById(R.id.trend_image);
            trending_name = itemView.findViewById(R.id.trend_name);
            trend_description = itemView.findViewById(R.id.trend_description);
        }
    }
}
