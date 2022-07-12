package com.fptu.android.project.model;

import java.io.Serializable;

public class Product implements Serializable {

    private String product_id;
    private String product_url;
    private String product_name;
    private String categoryId;
    private String description;
    private int product_price;
    private int quantity;
    private String currentDate;

    public Product() {

    }

    public Product(String product_url) {
        this.product_url = product_url;
    }

    public Product(String product_id, String product_url, String product_name, String categoryId, String description, int product_price, int quantity) {
        this.product_id = product_id;
        this.product_url = product_url;
        this.product_name = product_name;
        this.categoryId = categoryId;
        this.description = description;
        this.product_price = product_price;
        this.quantity = quantity;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_url() {
        return product_url;
    }

    public void setProduct_url(String product_url) {
        this.product_url = product_url;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}
