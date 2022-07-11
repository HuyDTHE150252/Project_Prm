package com.fptu.android.project.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fptu.android.project.R;
import com.fptu.android.project.activity.restaurant.RestaurantCrudActivity;
import com.fptu.android.project.activity.restaurant.ShowActivity;
import com.fptu.android.project.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.fptu.android.project.model.Product;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
      private ShowActivity activity;
      private List<Product> pList;
      private FirebaseFirestore db = FirebaseFirestore.getInstance();



      public MyAdapter(ShowActivity activity, List<Product> pList){
          this.activity = activity;
          this.pList = pList;
      }
      public void updateData(int position){
          Product item = pList.get(position);
          Bundle bundle = new Bundle();
          bundle.putString("id", item.getProduct_id());
          bundle.putString("name", item.getProduct_name());
          bundle.putString("price", String.valueOf(item.getProduct_price()));
          bundle.putString("description", String.valueOf(item.getDescription()));
          bundle.putString("quantity", String.valueOf(item.getQuantity()));
          bundle.putString("cate_id", item.getCategoryId());
          //bundle.putString("rate",String.valueOf(item.getRate()));
          Intent intent = new Intent(activity, RestaurantCrudActivity.class);
          intent.putExtras(bundle);
          activity.startActivity(intent);
//          Log.u("Write1", "Error getting documents.", task.getException());

      }
    public void deleteData(int position){
        Product item = pList.get(position);
        db.collection("product").document(item.getProduct_id()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(activity, "Delete is sussecful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "Delete is fail" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
    });

}



private void notifyRemoved(int position){
          pList.remove(position);
          notifyRemoved(position);
          activity.getListProduct();
}
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.product_item, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(activity).load(pList.get(position).getProduct_url()).into(holder.product_img);
        holder.name.setText(pList.get(position).getProduct_name());
        holder.description.setText(pList.get(position).getDescription());
        holder.price.setText(String.valueOf(pList.get(position).getProduct_price()));
    }

    @Override
    public int getItemCount() {
        return pList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  name, description, price,rate;
        ImageView product_img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            product_img = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.product_description);
            price = itemView.findViewById(R.id.product_price);
            rate = itemView.findViewById(R.id.product_rate);



        }
    }
}
