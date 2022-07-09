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
import com.fptu.android.project.activity.ProductDetailActivity;
import com.fptu.android.project.model.Product;

import java.util.List;

public class    ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private Context context;
    private List<Product> listProduct;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public ProductAdapter(Context context, List<Product> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    public void setData(List<Product> list) {
        this.listProduct = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = listProduct.get(position);
        if(product == null){
            return;
        }
        holder.product_img.setImageResource(product.getProduct_image());
        holder.product_name.setText(product.getProduct_name());
        holder.product_address.setText(product.getProduct_address());
        holder.rate.setText(product.getRate());
        holder.status.setText(product.getStatus());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("detailed",product);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView product_img;
        private TextView product_name;
        private TextView product_address;
        private TextView rate;
        private TextView status;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            product_img = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            product_address = itemView.findViewById(R.id.product_address);
            rate = itemView.findViewById(R.id.product_rate);
            status = itemView.findViewById(R.id.product_status);
        }
    }
}
