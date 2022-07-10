package com.fptu.android.project.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
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
import com.fptu.android.project.activity.restaurant.RestaurantActivity;
import com.fptu.android.project.activity.shipper.ShipperActivity;
import com.fptu.android.project.activity.user.LoginActivity;
import com.fptu.android.project.adapter.OrderHistoryAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomePageActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView nav_menu;
    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth=FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);
        nav_menu = findViewById(R.id.nav_view);
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
                        if (auth.getCurrentUser() != null) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartFragment()).commit();

                        } else {
                            Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
                            Toast.makeText(HomePageActivity.this, "Login first then Add to cart", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                        break;
                    case R.id.proflie:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
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
                    case R.id.restaurant:
                        Intent i = new Intent(HomePageActivity.this, RestaurantActivity.class);
                        Toast.makeText(HomePageActivity.this, "List Restaurant", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                        break;
                    case R.id.locationUser:
                        startActivity(new Intent(HomePageActivity.this, GoogmapActivity.class));
                        break;
                    case R.id.allOrder:
                        startActivity(new Intent(HomePageActivity.this, ShipperActivity.class));
                        break;
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