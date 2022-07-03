package com.fptu.android.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.fptu.android.project.R;

public class AddAddressActivity extends AppCompatActivity {
    EditText fname,lname,phone,address;
    Button addAddress;
    Toolbar toolbar;
    void bidingView(){
        toolbar=findViewById(R.id.add_address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fname=findViewById(R.id.address_fname);
        lname=findViewById(R.id.address_lname);
        address=findViewById(R.id.address_city);
        phone=findViewById(R.id.address_phone);
        addAddress=findViewById(R.id.add_address_btn);

    }

    private void setSupportActionBar(Toolbar toolbar) {

    }

    void bidingAction(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        bidingView();
        bidingAction();
    }
}