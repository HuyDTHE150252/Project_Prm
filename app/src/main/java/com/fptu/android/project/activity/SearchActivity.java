package com.fptu.android.project.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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

public class SearchActivity extends AppCompatActivity {

    private SearchView search;
    private RecyclerView rcvSearchProductList;
    private ProductAdapter productAdapter;
    private List<Product> searchList;
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    private void bindingView() {
        search = findViewById(R.id.search_view);
        rcvSearchProductList = findViewById(R.id.rcv_searchProductList);
        productAdapter = new ProductAdapter(this);
        searchList = new ArrayList<>();
    }

    private void bindingAction() {
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvSearchProductList.setLayoutManager(linearLayoutManager);
        productAdapter.setData(searchList);
        database.collection("product")
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
                            p.setQuantity(Integer.valueOf(doc.get("quantity").toString()));
                            searchList.add(p);
                        }
                        productAdapter.notifyDataSetChanged();
                    }
                });
        rcvSearchProductList.setAdapter(productAdapter);
    }

    private void filterList(String name) {
        List<Product> newList = new ArrayList<>();
        for(Product product : searchList){
            if(product.getProduct_name().toLowerCase().contains(name.toLowerCase())){
                newList.add(product);
            }
        }

        if(newList.isEmpty()){
            searchList.clear();
            productAdapter.setData(newList);
        }else{
            productAdapter.setData(newList);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bindingView();
        bindingAction();
    }
}

