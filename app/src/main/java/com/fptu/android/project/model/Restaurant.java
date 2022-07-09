package com.fptu.android.project.model;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private String restaurant_image;
    private int restaurant_id;
    private int uid;
    private String restaurant_name;
    private String restaurant_address;
    private String restaurant_rate;
    private String restaurant_status;
    private String   des;






    public Restaurant(String restaurant_image, String restaurant_name, String restaurant_address, String restaurant_rate, String restaurant_status) {

        this.restaurant_image = restaurant_image;
        this.restaurant_name = restaurant_name;
        this.restaurant_address = restaurant_address;
        this.restaurant_rate = restaurant_rate;
        this.restaurant_status = restaurant_status;
    }


    public Restaurant() {
    }




    public String getRestaurant_image() {
        return restaurant_image;
    }

    public void setRestaurant_image(String restaurant_image) {
        this.restaurant_image = restaurant_image;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_address() {
        return restaurant_address;
    }

    public void setRestaurant_address(String restaurant_address) {
        this.restaurant_address = restaurant_address;
    }

    public String getRestaurant_rate() {
        return restaurant_rate;
    }

    public void setRestaurant_rate(String restaurant_rate) {
        this.restaurant_rate = restaurant_rate;
    }

    public String getRestaurant_status() {
        return restaurant_status;
    }

    public void setRestaurant_status(String restaurant_status) {
        this.restaurant_status = restaurant_status;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurant_image='" + restaurant_image + '\'' +
                ", restaurant_id=" + restaurant_id +
                ", uid=" + uid +
                ", restaurant_name='" + restaurant_name + '\'' +
                ", restaurant_address='" + restaurant_address + '\'' +
                ", restaurant_rate='" + restaurant_rate + '\'' +
                ", restaurant_status='" + restaurant_status + '\'' +
                ", des='" + des + '\'' +
                '}';
    }
}
