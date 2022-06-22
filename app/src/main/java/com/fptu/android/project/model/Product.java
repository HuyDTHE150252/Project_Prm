package com.fptu.android.project.model;

import java.io.Serializable;

public class Product implements Serializable {

    private int product_image;
    private String product_name;
    private String product_address;
    private String rate;
    private String status;

    public Product(int product_image, String product_name, String product_address, String rate, String status) {
        this.product_image = product_image;
        this.product_name = product_name;
        this.product_address = product_address;
        this.rate = rate;
        this.status = status;
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
