package com.fptu.android.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.fragment.CartFragment;
import com.fptu.android.project.activity.fragment.HomeFragment;
import com.fptu.android.project.activity.fragment.ProfileFragment;
import com.fptu.android.project.activity.ggmap.GoogmapActivity;

import com.fptu.android.project.activity.restaurant.RestaurantCrudActivity;
import com.fptu.android.project.activity.shipper.ShipperActivity;
import com.fptu.android.project.activity.user.LoginActivity;
import com.fptu.android.project.model.User;

import com.fptu.android.project.model.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class HomePageActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView nav_menu;
    ProgressBar progressBar;
    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        HomeFragment homeFragment = new HomeFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);
        nav_menu = findViewById(R.id.nav_view);
        firestore = FirebaseFirestore.getInstance();

        nav_menu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homepage:

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment())
                                .commit();
                        refreshFragmentUI(homeFragment);

                        break;
                    case R.id.cart:
//                        if (auth.getCurrentUser() != null) {
//                            if (auth.getCurrentUser().isEmailVerified()) {
//                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartFragment()).commit();
//                            } Toast.makeText(HomePageActivity.this, "You have to verify your email to place orders!", Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
//                            Toast.makeText(HomePageActivity.this, "Login first then Add to cart", Toast.LENGTH_SHORT).show();
//                            startActivity(intent);
//                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartFragment()).commit();

                        break;
                    case R.id.proflie:
                        if (auth.getCurrentUser() != null) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                        } else {
                            //Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
                            Toast.makeText(HomePageActivity.this, "You have to login to see your profile!", Toast.LENGTH_SHORT).show();
                            //startActivity(intent);
                        }
                        break;

                    case R.id.logoutUser:
                        auth.signOut();//logout
                        startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
                        break;
                    case R.id.orderUser:
                        Intent intent = new Intent(HomePageActivity.this, OrderHistoryActivity.class);
                        Toast.makeText(HomePageActivity.this, "List order", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        break;
                    case R.id.locationUser:
                        startActivity(new Intent(HomePageActivity.this, GoogmapActivity.class));
                        break;
                    case R.id.allOrder:
                        if (auth.getCurrentUser().getProviderData().get(0).equals("admin")) {

                            startActivity(new Intent(HomePageActivity.this, ShipperActivity.class));
                            break;
                        }
                        Toast.makeText(HomePageActivity.this, "Only Shipper and Admin can see current orders!", Toast.LENGTH_SHORT).show();

                    case R.id.restaurant_product:
                        if (auth.getCurrentUser().getEmail().equals("tuanduong144@gmail.com")) {
                            startActivity(new Intent(HomePageActivity.this, RestaurantCrudActivity.class));
                            break;
                        }
                        Toast.makeText(HomePageActivity.this, "Only Admin can add products!", Toast.LENGTH_SHORT).show();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void refreshFragmentUI(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .detach(fragment)
                .attach(fragment)
                .commit();

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            setContentView(R.layout.activity_homepage);

            HomeFragment homeFragment = new HomeFragment();
            auth = FirebaseAuth.getInstance();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        }
    }
}