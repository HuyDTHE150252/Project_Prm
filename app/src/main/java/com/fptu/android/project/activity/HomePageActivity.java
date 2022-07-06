package com.fptu.android.project.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.fragment.CartFragment;
import com.fptu.android.project.activity.fragment.HomeFragment;
import com.fptu.android.project.activity.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomePageActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView nav_menu;
    private BottomNavigationView bottomnav;
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
        drawerLayout = findViewById(R.id.drawer_layout);
        nav_menu = findViewById(R.id.nav_view);
        nav_menu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homepage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                        break;
                    case R.id.cart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartFragment()).commit();
                        break;
                    case R.id.proflie:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
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

//        private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment selectedFragment = null;
//                switch (item.getItemId()) {
//                    case R.id.homepage:
//                        selectedFragment = new HomeFragment();
//                        break;
//                    case R.id.cart:
//                        if (auth.getCurrentUser() != null) {
//                            selectedFragment = new CartFragment();
//                        } else {
//
//                            Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
//                            Toast.makeText(HomePageActivity.this, "Login first then Add to cart", Toast.LENGTH_SHORT).show();
//                            startActivity(intent);
//                        }
//
//
//                        break;
//                    case R.id.proflie:
//                        selectedFragment = new ProfileFragment();
//                        break;
//                }
//
//                getSupportFragmentManager().
//
//                        beginTransaction().
//
//                        replace(R.id.fragment_container, selectedFragment).
//
//                        commit();
//                return true;
//            }
//        }

}