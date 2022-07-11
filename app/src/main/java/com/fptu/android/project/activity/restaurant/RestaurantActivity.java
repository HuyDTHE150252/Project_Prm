package com.fptu.android.project.activity.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.fptu.android.project.R;
import com.fptu.android.project.adapter.RestaurantAdapter;
import com.fptu.android.project.model.Order;
import com.fptu.android.project.model.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {
    TextView restaurant_view;
    FirebaseFirestore db;
    FirebaseAuth auth;
    List<Restaurant> resList;
    private ViewFlipper viewFlipper;
    private RecyclerView restaurant;

    Restaurant res;


    private RestaurantAdapter restaurant_adapter;
    private void bindingView() {
        viewFlipper = findViewById(R.id.slider_viewFlipper);
        restaurant = findViewById(R.id.restaurant_recyclerView);

        restaurant_adapter = new RestaurantAdapter(this, resList);

        restaurant.setLayoutManager(new LinearLayoutManager(this));

        restaurant.setAdapter(restaurant_adapter);
        restaurant_view = findViewById(R.id.textRestaurantestaurant);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        //res = new Restaurant();
        resList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listrestaurant);
        bindingView();
        getAllListRestaurant();
        System.out.println("-----------------------------------------------------");

        //bindingAction();
        //insertRestaurants();

    }
    private void getAllListRestaurant() {
        db.collection("restaurant").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        String img = documentSnapshot.getString("restaurant_img");
                        String rid = documentSnapshot.getString("restaurant_id");
                        String  name = documentSnapshot.getString("restaurant_name");
                        String rate = documentSnapshot.getString("restaurant_rate");
                        String status = documentSnapshot.getString("restaurant_status");
                        String address = documentSnapshot.getString("restaurant_address");
                        res = new Restaurant(img,name,address,rate,status);
                        resList.add(res);
                        restaurant_adapter.notifyDataSetChanged();
                        //recyclerView.setVisibility(View.VISIBLE);



                        Log.d("Write1", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                    }
                    // calculateTotalAmount(cartList);

                } else {
                    Log.w("Write1", "Error getting documents.", task.getException());
                }
            }
        });
    }

//    private List<Restaurant> getListRestaurant() {
//        List<Restaurant> list = new ArrayList<>();
//
//        list.add(new Restaurant(R.drawable.nhahangngon, 1, 1 , "Nam Le Quan", "Thon 3 Thach Hoa", "4.5","Mo cua","Nhiều món ngon"));
//        list.add(new Restaurant(R.drawable.do_an_nhanh, 2, 2 , "Nem Nuong Hung Anh", "Thon 3 Thach Hoa", "4.5","Mo cua","Nhiều món ngon"));
//        list.add(new Restaurant(R.drawable.do_an_vat, 3, 3 , "Bun Dau", "Thon 3 Thach Hoa", "4.5","Mo cua","Nhiều món ngon"));
//        list.add(new Restaurant(R.drawable.do_an_nhanh, 4, 4 , "My Cay Anh Hai", "Thon 3 Thach Hoa", "4.5","Mo cua","Nhiều món ngon"));
//        list.add(new Restaurant(R.drawable.nhahangngon, 5, 5 , "Com Minh Hanh", "Thon 3 Thach Hoa", "4.5","Mo cua","Nhiều món ngon"));
//
//
//        return list;
//    }
//    public void insertRestaurants(){
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("restaurants");
//        reference.setValue(getListRestaurant());
//
//
//    }
}