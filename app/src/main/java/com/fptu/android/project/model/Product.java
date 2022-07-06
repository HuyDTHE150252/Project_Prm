package com.fptu.android.project.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    private int product_id;
    private int product_image;
    private String product_name;
    private String product_address;
    private String rate;
    private String status;
    private ArrayList<Category> categories;
    private int quantity,price;
    private int restaurentId;

    public int getRestaurentId() {
        return restaurentId;
    }



    public void setRestaurentId(int restaurentId) {
        this.restaurentId = restaurentId;
    }

    public Product(int product_id, int product_image, String product_name, String product_address, String rate, String status, ArrayList<Category> categories, int quantity, int price) {
        this.product_id = product_id;
        this.product_image = product_image;
        this.product_name = product_name;
        this.product_address = product_address;
        this.rate = rate;
        this.status = status;
        this.categories = categories;
        this.quantity = quantity;
        this.price = price;
    }

    public Product(int product_image, String product_name, String product_address, String rate, String status, ArrayList<Category> categories) {
        this.product_image = product_image;
        this.product_name = product_name;
        this.product_address = product_address;
        this.rate = rate;
        this.status = status;
        this.categories = categories;
    }

    public Product(int product_image, String product_name, String product_address, String rate, String status, int quantity, int price,int restaurentId) {
        this.product_image = product_image;
        this.product_name = product_name;
        this.product_address = product_address;
        this.rate = rate;
        this.status = status;
        this.quantity = quantity;
        this.price = price;
        this.restaurentId=restaurentId;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getProduct_image() {
        return product_image;
    }

    public void setProduct_image(int product_image) {
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_address() {
        return product_address;
    }

    public void setProduct_address(String product_address) {
        this.product_address = product_address;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_image=" + product_image +
                ", product_name='" + product_name + '\'' +
                ", product_address='" + product_address + '\'' +
                ", rate='" + rate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
