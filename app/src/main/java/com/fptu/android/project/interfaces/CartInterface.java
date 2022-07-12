package com.fptu.android.project.interfaces;

public interface CartInterface {

    void addToCart(String uid,String name,String quantity,int total);

    void cleanCart();
}
