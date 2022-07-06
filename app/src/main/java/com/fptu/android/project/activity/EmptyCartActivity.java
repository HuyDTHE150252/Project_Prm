package com.fptu.android.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fptu.android.project.R;

public class EmptyCartActivity extends AppCompatActivity {
     Button btnShopping;
     void bidingView(){
         btnShopping=findViewById(R.id.btnShoppingEmptyCart);
     }
     void bidingAction(){
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
        bidingView();
        bidingAction();
    }
}