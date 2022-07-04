package com.fptu.android.project.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fptu.android.project.activity.ProductDetailActivity;
import com.fptu.android.project.interfaces.CartInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CartService implements CartInterface {
    FirebaseFirestore firestore;

    FirebaseAuth auth;
    public CartService() {
    }

    @Override
    public void addToCart() {

    }


}