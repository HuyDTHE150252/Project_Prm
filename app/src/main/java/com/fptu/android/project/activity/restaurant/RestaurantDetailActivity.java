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
    private TextView name,address,des;
    private ViewFlipper viewFlipper;



     Restaurant restaurant;

    private void bindingView() {
        imgRestaurant = findViewById(R.id.resdetail_image);
        name=findViewById(R.id.resdetail_name);
        address=findViewById(R.id.desdetail_shopaddress);
        des=findViewById(R.id.resdetail_description);

        viewFlipper = findViewById(R.id.slider_viewFlipper);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");
        bindingView();
//        imgRestaurant.setImageResource(restaurant.getRestaurant_image());
        name.setText(restaurant.getRestaurant_name());
        address.setText(restaurant.getRestaurant_address());
        des.setText(restaurant.getDes());

    }


    }