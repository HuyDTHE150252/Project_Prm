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
    private String res_name;
    private String res_address;
    private int status;

    public Product() {

    }

    public Product(String product_url) {
        this.product_url = product_url;
    }




    public Product(String product_id, String product_url, String product_name, String categoryId, String description, int product_price, int quantity, String res_name, String res_address, int status) {
        this.product_id = product_id;
        this.product_url = product_url;
        this.product_name = product_name;
        this.categoryId = categoryId;
        this.description = description;
        this.product_price = product_price;
        this.quantity = quantity;
        this.res_name = res_name;
        this.res_address = res_address;
        this.status = status;
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

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getRes_address() {
        return res_address;
    }

    public void setRes_address(String res_address) {
        this.res_address = res_address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
