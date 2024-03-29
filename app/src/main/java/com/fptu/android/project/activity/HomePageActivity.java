package com.fptu.android.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.fptu.android.project.activity.restaurant.RestaurantCrudActivity;
import com.fptu.android.project.activity.shipper.ShipperActivity;
import com.fptu.android.project.activity.user.LoginActivity;
import com.fptu.android.project.games.smartquiz.SmartQuiz;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class HomePageActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView nav_menu;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    private String currentRole;

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
        if (auth.getCurrentUser() != null) {
            firestore.collection("users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    currentRole = documentSnapshot.getString("role");
                    nav_menu.getMenu().findItem(R.id.restaurant_product).setVisible(false);
                    nav_menu.getMenu().findItem(R.id.allOrder).setVisible(false);
                    System.out.println("currentrole: "+currentRole);
                    if (currentRole.equals("admin")) {
                        nav_menu.getMenu().findItem(R.id.restaurant_product).setVisible(true);
                        nav_menu.getMenu().findItem(R.id.orderUser).setVisible(false);
                        nav_menu.getMenu().findItem(R.id.cart).setVisible(false);
                        nav_menu.getMenu().findItem(R.id.allOrder).setVisible(false);
                    }
                    if (currentRole.equals("shipper")) {
                        nav_menu.getMenu().findItem(R.id.restaurant_product).setVisible(false);
                        nav_menu.getMenu().findItem(R.id.orderUser).setVisible(false);
                        nav_menu.getMenu().findItem(R.id.cart).setVisible(false);
                        nav_menu.getMenu().findItem(R.id.allOrder).setVisible(true);
                    }
                }
            });

        }

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
                            Intent i = new Intent(HomePageActivity.this, LoginActivity.class);
                            Toast.makeText(HomePageActivity.this, "Login first then Add to cart", Toast.LENGTH_SHORT).show();
                            startActivity(i);
                        }
                        break;
                    case R.id.proflie:
                        if (auth.getCurrentUser() != null) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                        } else {
                            Toast.makeText(HomePageActivity.this, "You have to login to see your profile!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.aboutApp:
                        if (auth.getCurrentUser() != null) {
                            startActivity(new Intent(HomePageActivity.this, RestaurantActivity.class));

                        } else {
                            Intent i = new Intent(HomePageActivity.this, LoginActivity.class);
                            Toast.makeText(HomePageActivity.this, "Login first then review", Toast.LENGTH_SHORT).show();
                            startActivity(i);
                        }
                        break;
                    case R.id.logoutUser:
                        auth.signOut();//logout
                        startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
                        break;
                    case R.id.orderUser:
                        if (auth.getCurrentUser() != null) {
                            Intent intent = new Intent(HomePageActivity.this, OrderHistoryActivity.class);
                            Toast.makeText(HomePageActivity.this, "List order", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        } else {
                            Toast.makeText(HomePageActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case R.id.locationUser:
                        startActivity(new Intent(HomePageActivity.this, GoogmapActivity.class));
                        break;
                    case R.id.allOrder:
                        if (auth.getCurrentUser() != null) {
                            startActivity(new Intent(HomePageActivity.this, ShipperActivity.class));
                        } else {
                            Toast.makeText(HomePageActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case R.id.restaurant_product:
                        startActivity(new Intent(HomePageActivity.this, RestaurantCrudActivity.class));
                        break;
                    case R.id.smartQuiz:
                        SmartQuiz quiz = new SmartQuiz();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, quiz)
                                .commit();
                        quiz.setCallback(new SmartQuiz.ExitGame() {
                            @Override
                            public void getNewFragment() {
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment())
                                        .commit();
                            }
                        });

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