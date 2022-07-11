package com.fptu.android.project.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.adapter.CategoryAdapter;
import com.fptu.android.project.adapter.ProductAdapter;
import com.fptu.android.project.adapter.TrendingAdapter;
import com.fptu.android.project.model.Category;
import com.fptu.android.project.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewFlipper viewFlipper;
    int[] slider = {R.drawable.do_an_nhanh, R.drawable.do_an_vat, R.drawable.do_an_vat_2};
    private RecyclerView category, product, trending;
    private CategoryAdapter cate_adapter;
    private TrendingAdapter trend_adapter;
    private ProductAdapter product_adapter;
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
            trend_adapter.setData(getListTrending());
            trending.setAdapter(trend_adapter);

            LinearLayoutManager productLayoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
            product.setLayoutManager(productLayoutManager);
            product_adapter.setData(getListProduct());
            product.setAdapter(product_adapter);

        }
    }


    private List<Category> getListCategory() {
        List<Category> list = new ArrayList<>();
        list.add(new Category(1, "Food", "https://firebasestorage.googleapis.com/v0/b/projectprm-392.appspot.com/o/picture%2Ffood_icon.png?alt=media&token=dc332de1-5004-47a6-b8c2-473d73c1fe7ba"));
        list.add(new Category(2, "Drink", "https://firebasestorage.googleapis.com/v0/b/projectprm-392.appspot.com/o/picture%2Fdrink_icon.png?alt=media&token=da5a1021-9dab-47a6-ba0d-8a62f6853623"));
        list.add(new Category(3, "FastFood", "https://firebasestorage.googleapis.com/v0/b/projectprm-392.appspot.com/o/picture%2Ffast_food_icon.png?alt=media&token=b02e8786-89d8-45c0-8cc9-741406937d2c"));
        list.add(new Category(4, "RawFood", "https://firebasestorage.googleapis.com/v0/b/projectprm-392.appspot.com/o/picture%2Fraw_food_icon.png?alt=media&token=2f4cb48f-2fc1-40fa-85b2-02544505d00c"));
        list.add(new Category(5, "Fruit", "https://firebasestorage.googleapis.com/v0/b/projectprm-392.appspot.com/o/picture%2Ffruit_icon.png?alt=media&token=2d2cc5b1-30a4-4c98-8698-cc646841329a"));
        list.add(new Category(6, "IceCream", "https://firebasestorage.googleapis.com/v0/b/projectprm-392.appspot.com/o/picture%2Ficecream_icon.png?alt=media&token=eae11848-1ce0-466f-9b2b-c95f4d29219d"));
        list.add(new Category(7, "Cake", "https://firebasestorage.googleapis.com/v0/b/projectprm-392.appspot.com/o/picture%2Fbirthday_cake_icon.png?alt=media&token=3cb41ca1-2815-40b1-8bbc-834e30d2e859"));
        list.add(new Category(8, "Beer", "https://firebasestorage.googleapis.com/v0/b/projectprm-392.appspot.com/o/picture%2Fbeer_icon.png?alt=media&token=49538c20-b8aa-437e-ba47-929b7955edd2"));
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

                        }
                    }
                });

        return list;
    }

    private List<Product> getListTrending() {
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

                        }
                    }
                });

        return list;
    }
}
