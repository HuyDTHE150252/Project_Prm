package com.fptu.android.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.fptu.android.project.R;

public class EmptyCartActivity extends AppCompatActivity {
     Button btnShopping;

    void bindingView() {
        btnShopping = findViewById(R.id.btnShoppingEmptyCart);
    }

    void bindingAction() {
        btnShopping.setOnClickListener(this::shoppingContinue);
    }

    private void shoppingContinue(View view) {
         Intent intent= new Intent(EmptyCartActivity.this,HomePageActivity.class);
         startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_cart);
        bindingView();
        bindingAction();
    }
}