package com.fptu.android.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.fptu.android.project.R;
import com.fptu.android.project.adapter.CategoryAdapter;
import com.fptu.android.project.adapter.ProductAdapter;
import com.fptu.android.project.adapter.TrendingAdapter;
import com.fptu.android.project.model.Category;
import com.fptu.android.project.model.Product;
import com.fptu.android.project.model.User;
import com.fptu.android.project.service.UserService;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    private TextView product_view;
    private ViewFlipper viewFlipper;
    int[] slider = {R.drawable.do_an_nhanh, R.drawable.do_an_vat, R.drawable.do_an_vat_2};
    private RecyclerView category, product, trending;
    private CategoryAdapter cate_adapter;
    private TrendingAdapter trend_adapter;
    private ProductAdapter product_adapter;

    private void bindingView() {
        viewFlipper = findViewById(R.id.slider_viewFlipper);
        category = findViewById(R.id.category_recyclerView);
        trending = findViewById(R.id.trending_recyclerView);
        product = findViewById(R.id.product_recyclerView);
        cate_adapter = new CategoryAdapter(this);
        trend_adapter = new TrendingAdapter(this);
        product_adapter = new ProductAdapter(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        category.setLayoutManager(gridLayoutManager);
        cate_adapter.setData(getListCategory());
        category.setAdapter(cate_adapter);

        LinearLayoutManager trendingLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        trending.setLayoutManager(trendingLayoutManager);
        trend_adapter.setData(getListTrending());
        trending.setAdapter(trend_adapter);

        LinearLayoutManager productLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        product.setLayoutManager(productLayoutManager);
        product_adapter.setData(getListProduct());
        product.setAdapter(product_adapter);

        product_view = findViewById(R.id.product);
    }

    private void bindingAction() {
        for (int i = 0; i < slider.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(slider[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

//        product_view.setOnClickListener(this::OnProductClick);
    }

//    private void OnProductClick(View v) {
//        Intent i = new Intent( this,ProductActivity.class);
//        startActivity(i);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindingView();
        bindingAction();
        UserService s = new UserService();
        s.insert(null);
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
        list.add(new Category(R.drawable.food_icon, "Food"));
        list.add(new Category(R.drawable.drink_icon, "Drink"));
        list.add(new Category(R.drawable.fast_food_icon, "FastFood"));
        list.add(new Category(R.drawable.raw_food_icon, "RawFood"));
        list.add(new Category(R.drawable.fruit_icon, "Fruit"));
        list.add(new Category(R.drawable.icecream_icon, "IceCream"));
        list.add(new Category(R.drawable.birthday_cake_icon, "Cake"));
        list.add(new Category(R.drawable.beer_icon, "Beer"));

        return list;
    }

}