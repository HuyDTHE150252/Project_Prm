package com.fptu.android.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.model.Category;
import com.fptu.android.project.model.Product;

import org.w3c.dom.Text;

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
    public void onBindViewHolder(@NonNull TrendingViewHolder holder, int position) {
        Product trending = listTrending.get(position);
        if(trending == null){
            return;
        }
        holder.product_img.setImageResource(trending.getProduct_image());
        holder.product_name.setText(trending.getProduct_name());
        holder.product_address.setText(trending.getProduct_address());
        holder.rate.setText(trending.getRate());
        holder.status.setText(trending.getStatus());
    }

    @Override
    public int getItemCount() {
        return listTrending.size();
    }

    public class TrendingViewHolder extends RecyclerView.ViewHolder {

        private ImageView product_img;
        private TextView product_name;
        private TextView product_address;
        private TextView rate;
        private TextView status;

        public TrendingViewHolder(@NonNull View itemView) {
            super(itemView);
            product_img = itemView.findViewById(R.id.trend_image);
            product_name = itemView.findViewById(R.id.trend_name);
            product_address = itemView.findViewById(R.id.trend_address);
            rate = itemView.findViewById(R.id.trend_rate);
            status = itemView.findViewById(R.id.trend_status);
        }
    }
}
