package com.fptu.android.project.model;

import java.io.Serializable;

public class Product implements Serializable {

    private String product_id;
    private String product_url;
    private String product_name;
    private String description;
    private String res_name;
    private String res_address;
    private int product_price;
    private String type;
    private float rate;
    private int status;
    private int quantity;

    public Product(String product_name, String description, String res_address, int product_price, float rate) {
        this.product_name = product_name;
        this.description = description;
        this.res_address = res_address;
        this.product_price = product_price;
        this.rate = rate;
    }

    public Product() {

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product(String product_id, String product_url, String product_name, String description, String res_name, String res_address, int product_price, String type, float rate, int status, int quantity) {
        this.product_id = product_id;
        this.product_url = product_url;
        this.product_name = product_name;
        this.description = description;
        this.res_name = res_name;
        this.res_address = res_address;
        this.product_price = product_price;
        this.type = type;
        this.rate = rate;
        this.status = status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
