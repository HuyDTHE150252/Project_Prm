package com.fptu.android.project.activity.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.HomePageActivity;
import com.fptu.android.project.adapter.MyRestaurantAdapter;
import com.fptu.android.project.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private MyRestaurantAdapter adapter;
    private List<Product> list;
    private Button btn_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        recyclerView = findViewById(R.id.recycleShowView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btn_Back = findViewById(R.id.backToHome);
        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new MyRestaurantAdapter(this, list);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        getListProduct();

        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ShowActivity.this, HomePageActivity.class));
            }
        });

    }

    public void getListProduct() {

        db.collection("product")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                            Product p = new Product();
                            p.setProduct_id(doc.get("id").toString());
                            p.setProduct_name(doc.get("name").toString());
                            p.setDescription(doc.get("description").toString());
                            p.setProduct_price(Integer.valueOf(doc.get("price").toString()));
                            p.setProduct_url(doc.get("url").toString());
                            list.add(p);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

}