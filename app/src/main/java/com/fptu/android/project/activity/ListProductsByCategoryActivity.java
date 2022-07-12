package com.fptu.android.project.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.adapter.ProductAdapter;
import com.fptu.android.project.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListProductsByCategoryActivity extends AppCompatActivity {

    private RecyclerView rcvListProductByCate;
    private ProductAdapter productAdapter;
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products_by_category);
        rcvListProductByCate = findViewById(R.id.recyclerView_listProductbyCate);
        productAdapter = new ProductAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvListProductByCate.setLayoutManager(linearLayoutManager);
        productAdapter.setData(getListProductbyCategory());
        rcvListProductByCate.setAdapter(productAdapter);

    }

    private List<Product> getListProductbyCategory() {
        List<Product> list = new ArrayList<>();
        String type = getIntent().getStringExtra("type");
        //getting Food
        if (type != null && type.equalsIgnoreCase("food")) {
            database.collection("product").whereEqualTo("type", "food").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        Product p = new Product();
                        p.setProduct_id(documentSnapshot.get("id").toString());
                        p.setProduct_name(documentSnapshot.get("name").toString());
                        p.setDescription(documentSnapshot.get("description").toString());
                        p.setProduct_price(Integer.valueOf(documentSnapshot.get("price").toString()));
                        p.setProduct_url(documentSnapshot.get("url").toString());
                        p.setQuantity(Integer.valueOf(documentSnapshot.get("quantity").toString()));
                        list.add(p);
                        productAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        //getting drink
        if (type != null && type.equalsIgnoreCase("drink")) {
            database.collection("product").whereEqualTo("type", "drink").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        Product p = new Product();
                        p.setProduct_id(documentSnapshot.get("id").toString());
                        p.setProduct_name(documentSnapshot.get("name").toString());
                        p.setDescription(documentSnapshot.get("description").toString());
                        p.setProduct_price(Integer.valueOf(documentSnapshot.get("price").toString()));
                        p.setProduct_url(documentSnapshot.get("url").toString());
                        p.setQuantity(Integer.valueOf(documentSnapshot.get("quantity").toString()));
                        list.add(p);
                        productAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        //getting fastfood
        if (type != null && type.equalsIgnoreCase("fastfood")) {
            database.collection("product").whereEqualTo("type", "fastfood").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        Product p = new Product();
                        p.setProduct_id(documentSnapshot.get("id").toString());
                        p.setProduct_name(documentSnapshot.get("name").toString());
                        p.setDescription(documentSnapshot.get("description").toString());
                        p.setProduct_price(Integer.valueOf(documentSnapshot.get("price").toString()));
                        p.setProduct_url(documentSnapshot.get("url").toString());
                        p.setQuantity(Integer.valueOf(documentSnapshot.get("quantity").toString()));
                        list.add(p);
                        productAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        //getting rawfood
        if (type != null && type.equalsIgnoreCase("rawfood")) {
            database.collection("product").whereEqualTo("type", "rawfood").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        Product p = new Product();
                        p.setProduct_id(documentSnapshot.get("id").toString());
                        p.setProduct_name(documentSnapshot.get("name").toString());
                        p.setDescription(documentSnapshot.get("description").toString());
                        p.setProduct_price(Integer.valueOf(documentSnapshot.get("price").toString()));
                        p.setProduct_url(documentSnapshot.get("url").toString());
                        p.setQuantity(Integer.valueOf(documentSnapshot.get("quantity").toString()));
                        list.add(p);
                        productAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        //getting fruit
        if (type != null && type.equalsIgnoreCase("fruit")) {
            database.collection("product").whereEqualTo("type", "fruit").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        Product p = new Product();
                        p.setProduct_id(documentSnapshot.get("id").toString());
                        p.setProduct_name(documentSnapshot.get("name").toString());
                        p.setDescription(documentSnapshot.get("description").toString());
                        p.setProduct_price(Integer.valueOf(documentSnapshot.get("price").toString()));
                        p.setProduct_url(documentSnapshot.get("url").toString());
                        p.setQuantity(Integer.valueOf(documentSnapshot.get("quantity").toString()));
                        list.add(p);
                        productAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        //getting icecream
        if (type != null && type.equalsIgnoreCase("icecream")) {
            database.collection("product").whereEqualTo("type", "icecream").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        Product p = new Product();
                        p.setProduct_id(documentSnapshot.get("id").toString());
                        p.setProduct_name(documentSnapshot.get("name").toString());
                        p.setDescription(documentSnapshot.get("description").toString());
                        p.setProduct_price(Integer.valueOf(documentSnapshot.get("price").toString()));
                        p.setProduct_url(documentSnapshot.get("url").toString());
                        p.setQuantity(Integer.valueOf(documentSnapshot.get("quantity").toString()));
                        list.add(p);
                        productAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        //getting cake
        if (type != null && type.equalsIgnoreCase("cake")) {
            database.collection("product").whereEqualTo("type", "cake").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        Product p = new Product();
                        p.setProduct_id(documentSnapshot.get("id").toString());
                        p.setProduct_name(documentSnapshot.get("name").toString());
                        p.setDescription(documentSnapshot.get("description").toString());
                        p.setProduct_price(Integer.valueOf(documentSnapshot.get("price").toString()));
                        p.setProduct_url(documentSnapshot.get("url").toString());
                        p.setQuantity(Integer.valueOf(documentSnapshot.get("quantity").toString()));
                        list.add(p);
                        productAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        //getting beer
        if (type != null && type.equalsIgnoreCase("beer")) {
            database.collection("product").whereEqualTo("type", "beer").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        Product p = new Product();
                        p.setProduct_id(documentSnapshot.get("id").toString());
                        p.setProduct_name(documentSnapshot.get("name").toString());
                        p.setDescription(documentSnapshot.get("description").toString());
                        p.setProduct_price(Integer.valueOf(documentSnapshot.get("price").toString()));
                        p.setProduct_url(documentSnapshot.get("url").toString());
                        p.setQuantity(Integer.valueOf(documentSnapshot.get("quantity").toString()));
                        list.add(p);
                        productAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        return list;
    }
}