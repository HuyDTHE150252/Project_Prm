package com.fptu.android.project.activity.restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.fptu.android.project.R;
import com.fptu.android.project.adapter.RestaurantAdapter;
import com.fptu.android.project.model.Restaurant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {
    private TextView restaurant_view;
    private ViewFlipper viewFlipper;
    //int[] slider = {R.drawable.do_an_nhanh, R.drawable.do_an_vat, R.drawable.do_an_vat_2};
    private RecyclerView restaurant;
//    private CategoryAdapter cate_adapter;
//    private TrendingAdapter trend_adapter;
    private RestaurantAdapter restaurant_adapter;
    private void bindingView() {
        viewFlipper = findViewById(R.id.slider_viewFlipper);
        restaurant = findViewById(R.id.restaurant_recyclerView);
//        trending = findViewById(R.id.trending_recyclerView);
//        product = findViewById(R.id.product_recyclerView);
//        cate_adapter = new CategoryAdapter(this);
//        trend_adapter = new TrendingAdapter(this);
        restaurant_adapter = new RestaurantAdapter(this);

        restaurant.setLayoutManager(new LinearLayoutManager(this));
        restaurant_adapter.setData(getListRestaurant());
        restaurant.setAdapter(restaurant_adapter);



        restaurant_view = findViewById(R.id.textRestaurantestaurant);
    }

//    private void bindingAction() {
//        for (int i = 0; i < slider.length; i++) {
//            ImageView imageView = new ImageView(this);
//            imageView.setImageResource(slider[i]);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            viewFlipper.addView(imageView);
//        }
//        viewFlipper.setFlipInterval(3000);
//        viewFlipper.setAutoStart(true);
//
////        product_view.setOnClickListener(this::OnProductClick);
//    }


//        product_view.setOnClickListener(this::OnProductClick);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listrestaurant);
        bindingView();
        //bindingAction();
        //insertRestaurants();

    }

    private List<Restaurant> getListRestaurant() {
        List<Restaurant> list = new ArrayList<>();
        list.add(new Restaurant(R.drawable.nhahangngon, 1, 1 , "Nam Le Quan", "Thon 3 Thach Hoa", "4.5","Mo cua","Nhiều món ngon"));
        list.add(new Restaurant(R.drawable.do_an_nhanh, 2, 2 , "Nem Nuong Hung Anh", "Thon 3 Thach Hoa", "4.5","Mo cua","Nhiều món ngon"));
        list.add(new Restaurant(R.drawable.do_an_vat, 3, 3 , "Bun Dau", "Thon 3 Thach Hoa", "4.5","Mo cua","Nhiều món ngon"));
        list.add(new Restaurant(R.drawable.birthday_cake_icon, 4, 4 , "My Cay Anh Hai", "Thon 3 Thach Hoa", "4.5","Mo cua","Nhiều món ngon"));
        list.add(new Restaurant(R.drawable.nhahangngon, 5, 5 , "Com Minh Hanh", "Thon 3 Thach Hoa", "4.5","Mo cua","Nhiều món ngon"));
        return list;
    }
    public void insertRestaurants(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("restaurants");
        reference.setValue(getListRestaurant());


    }
}