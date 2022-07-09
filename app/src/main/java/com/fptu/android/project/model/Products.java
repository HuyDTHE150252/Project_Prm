package com.fptu.android.project.model;

import java.util.ArrayList;

public class Products {

    //private String product_image;
    private String pid;
    private String product_name;
    private String restaurantId;
    private String categoryId;

    private String quantity, price;
    private String product_image;


    public Products() {
    }

    public Products(String pid, String product_name, String quantity, String price) {
        this.pid = pid;
        this.product_name = product_name;
        this.quantity = quantity;
        this.price = price;
    }

    public Products(String pid, String product_name, String quantity, String price, String product_image) {
        this.pid = pid;
        this.product_name = product_name;
        this.quantity = quantity;
        this.price = price;
        this.product_image = product_image;
    }

    public Products(String pid, String product_name, String restaurantId, String categoryId, String quantity, String price, String product_image) {
        this.pid = pid;
        this.product_name = product_name;
        this.restaurantId = restaurantId;
        this.categoryId = categoryId;
        this.quantity = quantity;
        this.price = price;
        this.product_image = product_image;
    }

    public Products(String pid, String product_name, String categoryId, String quantity, String price, String product_image) {
        this.pid = pid;
        this.product_name = product_name;
        this.categoryId = categoryId;
        this.quantity = quantity;
        this.price = price;
        this.product_image = product_image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Products{" +
                "pid='" + pid + '\'' +
                ", product_name='" + product_name + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                ", product_image='" + product_image + '\'' +
                '}';
    }
}
