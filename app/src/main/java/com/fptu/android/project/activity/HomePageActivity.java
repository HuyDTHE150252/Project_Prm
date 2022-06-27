package com.fptu.android.project.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

<<<<<<< HEAD
=======
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

>>>>>>> b153f4a599b686c75fa432c8a5554b861ca36536
import com.fptu.android.project.R;
import com.fptu.android.project.adapter.CategoryAdapter;
import com.fptu.android.project.adapter.ProductAdapter;
import com.fptu.android.project.adapter.TrendingAdapter;
import com.fptu.android.project.model.Category;
import com.fptu.android.project.model.Product;
import com.fptu.android.project.service.UserService;

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
    private Button login;
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
        setContentView(R.layout.activity_message_test);
        //bindingView();
        // bindingAction();
        UserService s = new UserService();
        EditText t = findViewById(R.id.message1);
        s.insert(null);
<<<<<<< HEAD
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s.sendMessage(t.getText().toString());
                s.getMessage();
            }
        });
    }
=======
>>>>>>> b153f4a599b686c75fa432c8a5554b861ca36536

// //Test connection
//        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
//        connectedRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                boolean connected = snapshot.getValue(Boolean.class);
//                if (connected) {
//                    System.out.println("connected");
//                } else {
//                    System.out.println("not connected");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                System.err.println("Listener was cancelled");
//            }
//
//        });
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