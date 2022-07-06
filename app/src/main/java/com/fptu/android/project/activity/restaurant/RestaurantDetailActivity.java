package com.fptu.android.project.activity.restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.fptu.android.project.R;
import com.fptu.android.project.adapter.ProductAdapter;
import com.fptu.android.project.adapter.RestaurantAdapter;
import com.fptu.android.project.model.Product;
import com.fptu.android.project.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailActivity extends AppCompatActivity {

    ImageView imgRestaurant;
    private TextView restaurant_detail_view;
    private ViewFlipper viewFlipper;
    private RecyclerView restaurant_detail;
    private ProductAdapter product_adapter;
     Product product;
     Restaurant restaurant;

    private void bindingView() {
        viewFlipper = findViewById(R.id.slider_viewFlipper);
        restaurant_detail = findViewById(R.id.menu_restaurant);
//        trending = findViewById(R.id.trending_recyclerView);
//        product = findViewById(R.id.product_recyclerView);
//        cate_adapter = new CategoryAdapter(this);
//        trend_adapter = new TrendingAdapter(this);
        product_adapter = new ProductAdapter(this);

        restaurant_detail.setLayoutManager(new LinearLayoutManager(this));
        //product_adapter.setData(getListByIdRestaurant(restaurant.getRestaurant_id()));
        restaurant_detail.setAdapter(product_adapter);



        restaurant_detail_view = findViewById(R.id.textViewMenu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        imgRestaurant = findViewById(R.id.img_restaurant);
        restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");
        imgRestaurant.setImageResource(restaurant.getRestaurant_image());
        bindingView();
    }

//    private List<Product> getListByIdRestaurant(int restaurant_id) {
//
//        List<Product> list = new ArrayList<>();
//        list.add(new Product(1, 1, R.drawable.ca_phe,"30000", "Cà phê", "19 Thôn 3, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "5.0", "ĐÓNG CỬA"));
//        list.add(new Product(2, 1, R.drawable.tra_sua_nuong, "30000", "Trà Sữa Nướng", "10A Thôn 2, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "4.9", "ĐÓNG CỬA"));
//        list.add(new Product(3, 2, R.drawable.tra_dao, "30000", "Trà Đào", "45 Thôn 9, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "4.7", "ĐÓNG CỬA"));
//        list.add(new Product(4, 2, R.drawable.tra_dao, "30000", "Đồ ăn Hàn", "12 Thôn 9, xã Tân Xã, huyện Thạch Thất, Hòa Lạc", "4.5", "ĐÓNG CỬA"));
//        list.add(new Product(5, 2, R.drawable.bun_dau_mam_tom, "30000", "Bún Đậu", "55 Thôn 3, thị trấn Liên Quan, huyện Thạch Thất, Hòa Lạc", "4.5", "ĐÓNG CỬA"));
//        list.add(new Product(6, 3, R.drawable.com_rang, "30000", "Cơm Rang", "1B Thôn 4, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "4.0", "ĐÓNG CỬA"));
//        list.add(new Product(7, 3, R.drawable.ga_u_muoi, "30000", "Gà Ủ Muối", "23 Thôn 8, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "4.0", "ĐÓNG CỬA"));
//        list.add(new Product(8, 4, R.drawable.ca_phe, "30000", "Cà phê", "19 Thôn 3, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "5.0", "ĐÓNG CỬA"));
//        list.add(new Product(9, 4, R.drawable.tra_sua_nuong, "30000", "Trà Sữa Nướng", "10A Thôn 2, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "4.9", "ĐÓNG CỬA"));
//        list.add(new Product(10, 4, R.drawable.tra_dao, "30000", "Trà Đào", "45 Thôn 9, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "4.7", "ĐÓNG CỬA"));
//        list.add(new Product(11, 1, R.drawable.tra_dao, "30000", "Đồ ăn Hàn", "12 Thôn 9, xã Tân Xã, huyện Thạch Thất, Hòa Lạc", "4.5", "ĐÓNG CỬA"));
//        list.add(new Product(12, 5, R.drawable.bun_dau_mam_tom, "30000", "Bún Đậu", "55 Thôn 3, thị trấn Liên Quan, huyện Thạch Thất, Hòa Lạc", "4.5", "ĐÓNG CỬA"));
//        list.add(new Product(13, 5, R.drawable.com_rang, "30000", "Cơm Rang", "1B Thôn 4, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "4.0", "ĐÓNG CỬA"));
//        list.add(new Product(14, 5, R.drawable.ga_u_muoi, "30000", "Gà Ủ Muối", "23 Thôn 8, xã Thạch Hòa, huyện Thạch Thất, Hòa Lạc", "4.0", "ĐÓNG CỬA"));


//        List<Product> getAll = new ArrayList<>();
//        for (Product pro : list
//        ) {
//            //if (pro.getRestaurant_id() == restaurant_id) {
//                getAll.add(pro);
//
//            }
//
//
////        if (product.getRestaurant_id() == restaurant.getRestaurant_id()){
////            return list;
////        }
//
//
//        }
//        //return getAll;
//
//    }

    }