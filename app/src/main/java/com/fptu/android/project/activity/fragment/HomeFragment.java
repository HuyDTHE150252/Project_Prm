package com.fptu.android.project.activity.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewFlipper viewFlipper;
    int[] slider = {R.drawable.do_an_nhanh, R.drawable.do_an_vat, R.drawable.do_an_vat_2};
    private RecyclerView category, product, trending;
    private CategoryAdapter cate_adapter;
    private TrendingAdapter trend_adapter;
    private ProductAdapter product_adapter;
    private ImageView cate_img;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

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

    private List<Product> getListProduct() {
        List<Product> list = new ArrayList<>();
        list.add(new Product(R.drawable.ca_phe, "Cà phê", "19 Thôn 3, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "5.0", "ĐÓNG CỬA"));
        list.add(new Product(R.drawable.tra_sua_nuong, "Trà Sữa Nướng", "10A Thôn 2, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "4.9", "ĐÓNG CỬA"));
        list.add(new Product(R.drawable.tra_dao, "Trà Đào", "45 Thôn 9, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "4.7", "ĐÓNG CỬA"));
        list.add(new Product(R.drawable.tra_dao, "Đồ ăn Hàn", "12 Thôn 9, xã Tân Xã, huyện Thạch Thất, Hòa Lạc", "4.5", "ĐÓNG CỬA"));
        list.add(new Product(R.drawable.bun_dau_mam_tom, "Bún Đậu", "55 Thôn 3, thị trấn Liên Quan, huyện Thạch Thất, Hòa Lạc", "4.5", "ĐÓNG CỬA"));
        list.add(new Product(R.drawable.com_rang, "Cơm Rang", "1B Thôn 4, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "4.0", "ĐÓNG CỬA"));
        list.add(new Product(R.drawable.ga_u_muoi, "Gà Ủ Muối", "23 Thôn 8, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "4.0", "ĐÓNG CỬA"));

        return list;
    }

    private List<Product> getListTrending() {
        List<Product> list = new ArrayList<>();
        list.add(new Product(R.drawable.ca_phe, "Cà phê", "19 Thôn 3, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "5.0", "ĐÓNG CỬA"));
        list.add(new Product(R.drawable.tra_sua_nuong, "Trà Sữa Nướng", "10A Thôn 2, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "4.9", "ĐÓNG CỬA"));
        list.add(new Product(R.drawable.tra_dao, "Trà Đào", "45 Thôn 9, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "4.7", "ĐÓNG CỬA"));
        list.add(new Product(R.drawable.tra_dao, "Đồ ăn Hàn", "12 Thôn 9, xã Tân Xã, huyện Thạch Thất, Hòa Lạc", "4.5", "ĐÓNG CỬA"));
        list.add(new Product(R.drawable.bun_dau_mam_tom, "Bún Đậu", "55 Thôn 3, thị trấn Liên Quan, huyện Thạch Thất, Hòa Lạc", "4.5", "ĐÓNG CỬA"));
        list.add(new Product(R.drawable.com_rang, "Cơm Rang", "1B Thôn 4, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "4.0", "ĐÓNG CỬA"));
        list.add(new Product(R.drawable.ga_u_muoi, "Gà Ủ Muối", "23 Thôn 8, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "4.0", "ĐÓNG CỬA"));

        return list;
    }

    private List<Category> getListCategory() {
        List<Category> list = new ArrayList<>();
        DatabaseReference myRef = database.getInstance().getReference();
        Query query = myRef.child("category");
       query.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                   Category category = new Category();
                   category.setCate_id(Integer.valueOf(dataSnapshot.child("id").getValue().toString()));
                   category.setCate_name(dataSnapshot.child("name").getValue().toString());
                   category.setUrl(dataSnapshot.child("url").getValue().toString());
                   list.add(category);
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

        return list;
    }
}
