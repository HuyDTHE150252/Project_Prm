package com.fptu.android.project.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.SearchActivity;
import com.fptu.android.project.adapter.CategoryAdapter;
import com.fptu.android.project.adapter.ProductAdapter;
import com.fptu.android.project.adapter.TrendingAdapter;
import com.fptu.android.project.model.Category;
import com.fptu.android.project.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewFlipper viewFlipper;
    private TextView search;
    int[] slider = {R.drawable.do_an_nhanh, R.drawable.do_an_vat, R.drawable.do_an_vat_2};
    private RecyclerView category, product, trending;
    private CategoryAdapter cate_adapter;
    private TrendingAdapter trend_adapter;
    private ProductAdapter product_adapter;
    private SearchView searchView;
    FirebaseFirestore database = FirebaseFirestore.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {
            viewFlipper = view.findViewById(R.id.slider_viewFlipper);
            for (int i = 0; i < slider.length; i++) {
                ImageView imageView = new ImageView(view.getContext());
                imageView.setImageResource(slider[i]);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                viewFlipper.addView(imageView);
            }
            viewFlipper.setFlipInterval(3000);
            viewFlipper.setAutoStart(true);

            search = view.findViewById(R.id.search);
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentSearch = new Intent(view.getContext(), SearchActivity.class);
                    startActivity(intentSearch);
                }
            });

            category = view.findViewById(R.id.category_recyclerView);
            trending = view.findViewById(R.id.trending_recyclerView);
            product = view.findViewById(R.id.product_recyclerView);
            cate_adapter = new CategoryAdapter(view.getContext());
            trend_adapter = new TrendingAdapter(view.getContext());
            product_adapter = new ProductAdapter(view.getContext());

            GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 4, GridLayoutManager.VERTICAL, false);
            category.setLayoutManager(gridLayoutManager);
            cate_adapter.setData(getListCategory());
            category.setAdapter(cate_adapter);

            LinearLayoutManager trendingLayoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false);
            trending.setLayoutManager(trendingLayoutManager);
            trend_adapter.setData(getListNewest());
            trending.setAdapter(trend_adapter);

            LinearLayoutManager productLayoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
            product.setLayoutManager(productLayoutManager);
            product_adapter.setData(getListProduct());
            product.setAdapter(product_adapter);

        }
    }

    private List<Category> getListCategory() {
        List<Category> list = new ArrayList<>();
        database.collection("category")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                            Category c = new Category();
                            c.setCate_name(doc.get("name").toString());
                            c.setUrl(doc.get("url").toString());
                            c.setType(doc.get("type").toString());
                            list.add(c);
                            cate_adapter.notifyDataSetChanged();
                        }
                    }
                });
        return list;
    }

    private List<Product> getListProduct() {
        List<Product> list = new ArrayList<>();
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
                            list.add(p);
                            product_adapter.notifyDataSetChanged();

                        }
                    }
                });


        return list;
    }

    private List<Product> getListNewest() {
        List<Product> list = new ArrayList<>();
        database.collection("product").orderBy("date", Query.Direction.DESCENDING).limit(10)
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
                            list.add(p);
                            trend_adapter.notifyDataSetChanged();
                        }
                    }
                });

        return list;
    }

}
