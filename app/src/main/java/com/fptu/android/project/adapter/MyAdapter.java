package com.fptu.android.project.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.restaurant.RestaurantCrudActivity;
import com.fptu.android.project.activity.restaurant.ShowActivity;
import com.fptu.android.project.model.Model;
import com.fptu.android.project.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.fptu.android.project.model.Product;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
      private ShowActivity activity;
      private List<Model> pList;
      private FirebaseFirestore db = FirebaseFirestore.getInstance();



      public MyAdapter(ShowActivity activity, List<Model> pList){
          this.activity = activity;
          this.pList = pList;
      }
      public void updateData(int position){
          Model item = pList.get(position);
          Bundle bundle = new Bundle();
          bundle.putString("pid", item.getPid());
          bundle.putString("pimg", item.getPimg());
          bundle.putString("pprice", item.getPprice());
          bundle.putString("pname", item.getPname());
          Intent intent = new Intent(activity, RestaurantCrudActivity.class);
          intent.putExtras(bundle);
          activity.startActivity(intent);

      }
    public void deleteData(int position){
        Model item = pList.get(position);
        db.collection("restautantProduct").document(item.getPid()).delete()
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
          activity.showData();
}
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_detail_respro, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.img.setText(pList.get(position).getPimg());
        holder.price.setText(pList.get(position).getPprice());
        holder.name.setText(pList.get(position).getPname());


    }

    @Override
    public int getItemCount() {
        return pList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView img, price, name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_text);
            price = itemView.findViewById(R.id.price_text);
            name = itemView.findViewById(R.id.name_text);


        }
    }
}
