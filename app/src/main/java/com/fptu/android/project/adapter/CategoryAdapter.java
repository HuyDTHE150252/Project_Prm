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

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<Category> listcategory;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Category> list) {
        this.listcategory = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category cate = listcategory.get(position);
        if(cate == null){
            return;
        }
        holder.resourceImage.setImageResource(cate.getResourceImage());
        holder.cate_name.setText(cate.getCate_name());
    }

    @Override
    public int getItemCount() {
        return listcategory.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView resourceImage;
        private TextView cate_name;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            resourceImage = itemView.findViewById(R.id.cate_image);
            cate_name = itemView.findViewById(R.id.cate_name);
        }


    }
}
